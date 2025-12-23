package com.example.demo.controller;

import com.example.demo.model.SupplierRiskAlert;
import com.example.demo.service.SupplierRiskAlertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risk-alerts")
public class SupplierRiskAlertController {

    private final SupplierRiskAlertService riskAlertService;

    public SupplierRiskAlertController(SupplierRiskAlertService riskAlertService) {
        this.riskAlertService = riskAlertService;
    }

    @PostMapping
    public SupplierRiskAlert createAlert(@RequestBody SupplierRiskAlert alert) {
        return riskAlertService.createAlert(alert);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<SupplierRiskAlert> getAlertsBySupplier(@PathVariable Long supplierId) {
        return riskAlertService.getAlertsBySupplier(supplierId);
    }

    @PutMapping("/{id}/resolve")
    public SupplierRiskAlert resolveAlert(@PathVariable Long id) {
        return riskAlertService.resolveAlert(id);
    }

    @GetMapping
    public List<SupplierRiskAlert> getAllAlerts() {
        return riskAlertService.getAllAlerts();
    }
}
