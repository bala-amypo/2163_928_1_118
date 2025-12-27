package com.example.demo.controller;

import com.example.demo.model.DeliveryRecord;
import com.example.demo.service.DeliveryRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryRecordController {

    private final DeliveryRecordService service;

    public DeliveryRecordController(DeliveryRecordService service) {
        this.service = service;
    }

    @PostMapping
    public DeliveryRecord create(@RequestBody DeliveryRecord delivery) {
        return service.recordDelivery(delivery);
    }

    @GetMapping("/po/{poId}")
    public List<DeliveryRecord> getByPo(@PathVariable Long poId) {
        return service.getDeliveriesByPO(poId);
    }

    @GetMapping
    public List<DeliveryRecord> getAll() {
        return service.getAllDeliveries();
    }
}
