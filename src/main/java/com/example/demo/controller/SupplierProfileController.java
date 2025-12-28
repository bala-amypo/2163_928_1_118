package com.example.demo.controller;

import com.example.demo.model.SupplierProfile;
import com.example.demo.service.impl.SupplierProfileServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Suppliers")
public class SupplierProfileController {

    private final SupplierProfileServiceImpl service;

    public SupplierProfileController(SupplierProfileServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public SupplierProfile create(@RequestBody SupplierProfile supplier) {
        return service.createSupplier(supplier);
    }

    @GetMapping("/{id}")
    public SupplierProfile get(@PathVariable Long id) {
        return service.getSupplierById(id);
    }

    @GetMapping
    public List<SupplierProfile> getAll() {
        return service.getAllSuppliers();
    }

    @PutMapping("/{id}/status")
    public SupplierProfile updateStatus(@PathVariable Long id,
                                        @RequestParam boolean active) {
        return service.updateSupplierStatus(id, active);
    }

    @GetMapping("/lookup/{code}")
    public SupplierProfile lookup(@PathVariable String code) {
        return service.getBySupplierCode(code).orElse(null);
    }
}
