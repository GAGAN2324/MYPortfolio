package com.mf.backend.controller;

import com.mf.backend.ml.WekaService;
import com.mf.backend.model.PredictionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.*;
import java.util.List;

@RestController
@RequestMapping("/api/fund")
@CrossOrigin(origins = "http://localhost:5173")   // allow frontend to access backend
public class FundController {

    private final Path dataDir = Paths.get("data");

    public FundController() throws Exception {
        Files.createDirectories(dataDir);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws Exception {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        if (!filename.endsWith(".csv")) {
            return ResponseEntity.badRequest().body("Only CSV files allowed.");
        }
        Path out = dataDir.resolve(filename);
        Files.copy(file.getInputStream(), out, StandardCopyOption.REPLACE_EXISTING);
        return ResponseEntity.ok(filename);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listFiles() {
        try {
            List<String> files = Files.list(dataDir)
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .filter(name -> name.endsWith(".csv"))
                    .toList();

            return ResponseEntity.ok(files);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Unable to read files");
        }
    }

    @GetMapping("/predict")
    public ResponseEntity<?> predict(@RequestParam("file") String filename) {

        try {
            Path filePath = dataDir.resolve(filename);
            System.out.println("Looking for: " + filePath.toAbsolutePath());

            if (!Files.exists(filePath)) {
                return ResponseEntity.status(404)
                        .body("CSV file not found: " + filename);
            }

            WekaService.Result result = WekaService.trainAndPredict(filePath.toFile());

            String trend = "flat";
            List<Double> h = result.history;
            if (h != null && h.size() >= 2) {
                double first = h.get(0);
                double last = h.get(h.size() - 1);
                if (last > first) trend = "up";
                else if (last < first) trend = "down";
            }

            PredictionResponse pr = new PredictionResponse(
                    filename,
                    result.predicted,
                    result.rmse,
                    result.sharpe,
                    result.dates,
                    result.history,
                    result.avgNav,
                    result.minNav,
                    result.maxNav,
                    result.volatility,
                    trend
            );

            return ResponseEntity.ok(pr);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error processing prediction");
        }
    }
}
