package com.example.demo.controller;

import com.example.demo.model.SupplierRiskAlert;
import com.example.demo.service.SupplierRiskAlertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/risk-alerts")
public class SupplierRiskAlertController {

    private final SupplierRiskAlertService service;

    public SupplierRiskAlertController(SupplierRiskAlertService service) {
        this.service = service;
    }

    @PostMapping
    public SupplierRiskAlert create(@RequestBody SupplierRiskAlert alert) {
        return service.createAlert(alert);
    }

    @PutMapping("/{id}/resolve")
    public SupplierRiskAlert resolve(@PathVariable Long id) {
        return service.resolveAlert(id);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<SupplierRiskAlert> getBySupplier(@PathVariable Long supplierId) {
        return service.getAlertsBySupplier(supplierId);
    }

    @GetMapping
    public List<SupplierRiskAlert> getAll() {
        return service.getAllAlerts();
    }
}
