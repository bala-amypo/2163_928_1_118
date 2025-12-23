package com.example.demo.controller;

import com.example.demo.model.DeliveryRecord;
import com.example.demo.service.DeliveryRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryRecordController {

    private final DeliveryRecordService deliveryService;

    public DeliveryRecordController(DeliveryRecordService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public DeliveryRecord recordDelivery(@RequestBody DeliveryRecord delivery) {
        return deliveryService.recordDelivery(delivery);
    }

    @GetMapping("/po/{poId}")
    public List<DeliveryRecord> getByPO(@PathVariable Long poId) {
        return deliveryService.getDeliveriesByPO(poId);
    }

    @GetMapping
    public List<DeliveryRecord> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }
}
