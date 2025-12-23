package com.example.demo.controller;

import com.example.demo.model.DeliveryRecord;
import com.example.demo.service.DeliveryRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryRecordController {

    private final DeliveryRecordService deliveryRecordService;

    public DeliveryRecordController(DeliveryRecordService deliveryRecordService) {
        this.deliveryRecordService = deliveryRecordService;
    }

    @PostMapping
    public DeliveryRecord recordDelivery(@RequestBody DeliveryRecord delivery) {
        return deliveryRecordService.recordDelivery(delivery);
    }

    @GetMapping("/po/{poId}")
    public List<DeliveryRecord> getDeliveriesByPO(@PathVariable Long poId) {
        return deliveryRecordService.getDeliveriesByPO(poId);
    }

    @GetMapping
    public List<DeliveryRecord> getAllDeliveries() {
        return deliveryRecordService.getAllDeliveries();
    }
}
