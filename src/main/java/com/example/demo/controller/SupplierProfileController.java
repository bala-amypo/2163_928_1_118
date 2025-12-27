// package com.example.demo.controller;

// import com.example.demo.model.SupplierProfile;
// import com.example.demo.service.SupplierProfileService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/suppliers")
// public class SupplierProfileController {

//     private final SupplierProfileService service;

//     public SupplierProfileController(SupplierProfileService service) {
//         this.service = service;
//     }

//     @PostMapping
//     public SupplierProfile create(@RequestBody SupplierProfile supplier) {
//         return service.createSupplier(supplier);
//     }

//     @GetMapping("/{id}")
//     public SupplierProfile get(@PathVariable Long id) {
//         return service.getSupplierById(id);
//     }

//     @GetMapping
//     public List<SupplierProfile> getAll() {
//         return service.getAllSuppliers();
//     }

//     @PutMapping("/{id}")
//     public SupplierProfile updateStatus(@PathVariable Long id, @RequestParam boolean active) {
//         return service.updateSupplierStatus(id, active);
//     }
// }


package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.SupplierProfile;
import com.example.demo.service.SupplierProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierProfileController {

    private final SupplierProfileService service;

    public SupplierProfileController(SupplierProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SupplierProfile>> createSupplier(@RequestBody SupplierProfile supplier) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Created", service.createSupplier(supplier)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierProfile>> getSupplier(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Found", service.getSupplierById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplierProfile>>> getAllSuppliers() {
        return ResponseEntity.ok(new ApiResponse<>(true, "All Suppliers", service.getAllSuppliers()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<SupplierProfile>> updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Status Updated", service.updateSupplierStatus(id, active)));
    }
}