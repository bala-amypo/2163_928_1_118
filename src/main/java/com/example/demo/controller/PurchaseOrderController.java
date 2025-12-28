package com.example.demo.controller;

import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.service.impl.PurchaseOrderServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
@Tag(name = "Purchase Orders")
public class PurchaseOrderController {

    private final PurchaseOrderServiceImpl service;

    public PurchaseOrderController(PurchaseOrderServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public PurchaseOrderRecord create(@RequestBody PurchaseOrderRecord po) {
        return service.createPurchaseOrder(po);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<PurchaseOrderRecord> getBySupplier(@PathVariable Long supplierId) {
        return service.getPOsBySupplier(supplierId);
    }

    @GetMapping("/{id}")
    public PurchaseOrderRecord get(@PathVariable Long id) {
        return service.getPOById(id).orElse(null);
    }

    @GetMapping
    public List<PurchaseOrderRecord> getAll() {
        return service.getAllPurchaseOrders();
    }
}
