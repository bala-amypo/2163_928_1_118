package com.example.demo.controller;

import com.example.demo.model.DelayScoreRecord;
import com.example.demo.service.impl.DelayScoreServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delay-scores")
@Tag(name = "Delay Scores")
public class DelayScoreController {

    private final DelayScoreServiceImpl service;

    public DelayScoreController(DelayScoreServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/compute/{poId}")
    public DelayScoreRecord compute(@PathVariable Long poId) {
        return service.computeDelayScore(poId);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<DelayScoreRecord> bySupplier(@PathVariable Long supplierId) {
        return service.getScoresBySupplier(supplierId);
    }

    @GetMapping
    public List<DelayScoreRecord> getAll() {
        return service.getAllScores();
    }
}
