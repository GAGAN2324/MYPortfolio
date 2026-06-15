package com.mf.backend.model;

import java.util.List;

public class PredictionResponse {
    private String fundName;
    private double predictedNextNav;
    private double rmse;
    private double sharpe;
    private List<String> dates;        // history dates (strings)
    private List<Double> history;      // historical NAV values
    private double avgNav;
    private double minNav;
    private double maxNav;
    private double volatility;         // annualized volatility
    private String trend;              // "up" or "down" or "flat"

    public PredictionResponse() {}

    public PredictionResponse(String fundName, double predictedNextNav, double rmse, double sharpe,
                              List<String> dates, List<Double> history,
                              double avgNav, double minNav, double maxNav, double volatility, String trend) {
        this.fundName = fundName;
        this.predictedNextNav = predictedNextNav;
        this.rmse = rmse;
        this.sharpe = sharpe;
        this.dates = dates;
        this.history = history;
        this.avgNav = avgNav;
        this.minNav = minNav;
        this.maxNav = maxNav;
        this.volatility = volatility;
        this.trend = trend;
    }

    // getters + setters
    public String getFundName() { return fundName; }
    public void setFundName(String fundName) { this.fundName = fundName; }

    public double getPredictedNextNav() { return predictedNextNav; }
    public void setPredictedNextNav(double predictedNextNav) { this.predictedNextNav = predictedNextNav; }

    public double getRmse() { return rmse; }
    public void setRmse(double rmse) { this.rmse = rmse; }

    public double getSharpe() { return sharpe; }
    public void setSharpe(double sharpe) { this.sharpe = sharpe; }

    public List<String> getDates() { return dates; }
    public void setDates(List<String> dates) { this.dates = dates; }

    public List<Double> getHistory() { return history; }
    public void setHistory(List<Double> history) { this.history = history; }

    public double getAvgNav() { return avgNav; }
    public void setAvgNav(double avgNav) { this.avgNav = avgNav; }

    public double getMinNav() { return minNav; }
    public void setMinNav(double minNav) { this.minNav = minNav; }

    public double getMaxNav() { return maxNav; }
    public void setMaxNav(double maxNav) { this.maxNav = maxNav; }

    public double getVolatility() { return volatility; }
    public void setVolatility(double volatility) { this.volatility = volatility; }

    public String getTrend() { return trend; }
    public void setTrend(String trend) { this.trend = trend; }
}
