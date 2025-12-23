package com.example.demo.controller;

import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.service.PurchaseOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    public PurchaseOrderRecord createPO(@RequestBody PurchaseOrderRecord po) {
        return purchaseOrderService.createPurchaseOrder(po);
    }

    @GetMapping("/{id}")
    public PurchaseOrderRecord getPO(@PathVariable Long id) {
        return purchaseOrderService.getPOById(id).orElse(null);
    }

    @GetMapping("/supplier/{supplierId}")
    public List<PurchaseOrderRecord> getPOsBySupplier(@PathVariable Long supplierId) {
        return purchaseOrderService.getPOsBySupplier(supplierId);
    }

    @GetMapping
    public List<PurchaseOrderRecord> getAllPOs() {
        return purchaseOrderService.getAllPurchaseOrders();
    }
}
