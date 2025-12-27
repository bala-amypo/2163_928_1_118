// package com.example.demo.controller;

// import com.example.demo.model.PurchaseOrderRecord;
// import com.example.demo.service.PurchaseOrderService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/purchase-orders")
// public class PurchaseOrderController {

//     private final PurchaseOrderService service;

//     public PurchaseOrderController(PurchaseOrderService service) {
//         this.service = service;
//     }

//     @PostMapping
//     public PurchaseOrderRecord create(@RequestBody PurchaseOrderRecord po) {
//         return service.createPurchaseOrder(po);
//     }

//     @GetMapping("/{id}")
//     public PurchaseOrderRecord get(@PathVariable Long id) {
//         return service.getPOById(id).orElse(null);
//     }

//     @GetMapping("/supplier/{supplierId}")
//     public List<PurchaseOrderRecord> getBySupplier(@PathVariable Long supplierId) {
//         return service.getPOsBySupplier(supplierId);
//     }

//     @GetMapping
//     public List<PurchaseOrderRecord> getAll() {
//         return service.getAllPurchaseOrders();
//     }
// }

package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.service.PurchaseOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService service;

    public PurchaseOrderController(PurchaseOrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseOrderRecord>> createPO(@RequestBody PurchaseOrderRecord po) {
        return ResponseEntity.ok(new ApiResponse<>(true, "PO Created", service.createPurchaseOrder(po)));
    }

    @GetMapping("/supplier/{supplierId}")
    public ResponseEntity<ApiResponse<List<PurchaseOrderRecord>>> getBySupplier(@PathVariable Long supplierId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "POs fetched", service.getPOsBySupplier(supplierId)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseOrderRecord>>> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, "All POs", service.getAllPurchaseOrders()));
    }
}
