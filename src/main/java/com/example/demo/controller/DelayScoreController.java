// package com.example.demo.controller;

// import com.example.demo.model.DelayScoreRecord;
// import com.example.demo.service.DelayScoreService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/delay-scores")
// public class DelayScoreController {

//     private final DelayScoreService service;

//     public DelayScoreController(DelayScoreService service) {
//         this.service = service;
//     }

//     @PostMapping("/compute/{poId}")
//     public DelayScoreRecord compute(@PathVariable Long poId) {
//         return service.computeDelayScore(poId);
//     }

//     @GetMapping("/supplier/{supplierId}")
//     public List<DelayScoreRecord> getBySupplier(@PathVariable Long supplierId) {
//         return service.getScoresBySupplier(supplierId);
//     }

//     @GetMapping
//     public List<DelayScoreRecord> getAll() {
//         return service.getAllScores();
//     }
// }


package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.DelayScoreRecord;
import com.example.demo.service.DelayScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/delay-scores")
public class DelayScoreController {

    private final DelayScoreService service;

    public DelayScoreController(DelayScoreService service) {
        this.service = service;
    }

    @PostMapping("/compute/{poId}")
    public ResponseEntity<ApiResponse<DelayScoreRecord>> computeScore(@PathVariable Long poId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Computed", service.computeDelayScore(poId)));
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<ApiResponse<List<DelayScoreRecord>>> getBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Scores Fetched", service.getScoresBySupplier(supplierId)));
    }
}