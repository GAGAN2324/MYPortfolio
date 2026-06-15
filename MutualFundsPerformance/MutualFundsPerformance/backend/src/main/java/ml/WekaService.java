package com.mf.backend.ml;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WekaService {

    public static class Result {
        public double predicted;
        public double rmse;
        public Classifier model;

        public List<String> dates;
        public List<Double> history;

        public double avgNav;
        public double minNav;
        public double maxNav;

        public double volatility;
        public double sharpe;
    }

    public static Result trainAndPredict(File csvFile) throws Exception {

        // ---- READ CSV manually ----
        List<String> lines = Files.readAllLines(csvFile.toPath());

        List<String> dates = new ArrayList<>();
        List<Double> navs = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] p = lines.get(i).split(",");
            dates.add(p[0].trim());
            navs.add(Double.parseDouble(p[1].trim()));
        }

        // ---- BASIC STATS ----
        double sum = 0;
        double minV = Double.MAX_VALUE;
        double maxV = Double.MIN_VALUE;

        for (double v : navs) {
            sum += v;
            minV = Math.min(minV, v);
            maxV = Math.max(maxV, v);
        }

        double avg = sum / navs.size();

        // ---- Weka Loader (for model) ----
        CSVLoader loader = new CSVLoader();
        loader.setSource(csvFile);
        Instances data = loader.getDataSet();

        // remove date column
        if (data.attribute(0).name().equalsIgnoreCase("Date")) {
            data.deleteAttributeAt(0);
        }

        data.setClassIndex(data.numAttributes() - 1);

        Classifier model = new LinearRegression();
        model.buildClassifier(data);

        Evaluation eval = new Evaluation(data);
        eval.crossValidateModel(model, data, 5, new Random(1));

        // ---- Predict next value ----
        Instance last = data.lastInstance();

        Instance future = new DenseInstance(data.numAttributes());
        future.setDataset(data);

        for (int i = 0; i < data.numAttributes() - 1; i++) {
            future.setValue(i, last.value(i));
        }
        future.setMissing(data.classIndex());

        double predicted = model.classifyInstance(future);

        // ---- VOLATILITY + SHARPE ----
        List<Double> returns = new ArrayList<>();
        for (int i = 1; i < navs.size(); i++) {
            returns.add((navs.get(i) - navs.get(i - 1)) / navs.get(i - 1));
        }

        double meanR = returns.stream().mapToDouble(x -> x).average().orElse(0);
        double variance = 0;

        for (double r : returns) {
            variance += (r - meanR) * (r - meanR);
        }
        variance /= returns.size();

        double stdR = Math.sqrt(variance);

        // annualize assuming monthly returns
        double annualVolatility = stdR * Math.sqrt(12);
        double annualSharpe = (meanR * 12) / (annualVolatility == 0 ? 1 : annualVolatility);

        // ---- BUILD RESULT ----
        Result r = new Result();
        r.predicted = predicted;
        r.rmse = eval.rootMeanSquaredError();
        r.model = model;

        r.dates = dates;
        r.history = navs;

        r.avgNav = avg;
        r.minNav = minV;
        r.maxNav = maxV;

        r.volatility = annualVolatility;
        r.sharpe = annualSharpe;

        return r;
    }
}
