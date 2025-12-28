package com.example.demo.controller;

import com.example.demo.model.DeliveryRecord;
import com.example.demo.service.impl.DeliveryRecordServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@Tag(name = "Deliveries")
public class DeliveryRecordController {

    private final DeliveryRecordServiceImpl service;

    public DeliveryRecordController(DeliveryRecordServiceImpl service) {
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
