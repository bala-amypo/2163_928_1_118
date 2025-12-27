// package com.example.demo.controller;

// import com.example.demo.model.DeliveryRecord;
// import com.example.demo.service.DeliveryRecordService;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/deliveries")
// public class DeliveryRecordController {

//     private final DeliveryRecordService service;

//     public DeliveryRecordController(DeliveryRecordService service) {
//         this.service = service;
//     }

//     @PostMapping
//     public DeliveryRecord create(@RequestBody DeliveryRecord delivery) {
//         return service.recordDelivery(delivery);
//     }

//     @GetMapping("/po/{poId}")
//     public List<DeliveryRecord> getByPo(@PathVariable Long poId) {
//         return service.getDeliveriesByPO(poId);
//     }

//     @GetMapping
//     public List<DeliveryRecord> getAll() {
//         return service.getAllDeliveries();
//     }
// }


package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.DeliveryRecord;
import com.example.demo.service.DeliveryRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/delivery-records")
public class DeliveryRecordController {

    private final DeliveryRecordService service;

    public DeliveryRecordController(DeliveryRecordService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DeliveryRecord>> recordDelivery(@RequestBody DeliveryRecord record) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Recorded", service.recordDelivery(record)));
    }

    @GetMapping("/po/{poId}")
    public ResponseEntity<ApiResponse<List<DeliveryRecord>>> getByPO(@PathVariable Long poId) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched", service.getDeliveriesByPO(poId)));
    }
}