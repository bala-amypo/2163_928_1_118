package com.example.demo.controller;

import com.example.demo.model.DelayScoreRecord;
import com.example.demo.service.DelayScoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delay-scores")
public class DelayScoreController {

    private final DelayScoreService scoreService;

    public DelayScoreController(DelayScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping("/compute/{poId}")
    public DelayScoreRecord compute(@PathVariable Long poId) {
        return scoreService.computeDelayScore(poId);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<DelayScoreRecord> getBySupplier(@PathVariable Long supplierId) {
        return scoreService.getScoresBySupplier(supplierId);
    }

    @GetMapping
    public List<DelayScoreRecord> getAllScores() {
        return scoreService.getAllScores();
    }
}
