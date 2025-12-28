package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.DeliveryRecord;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.repository.PurchaseOrderRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryRecordServiceImpl {

    private DeliveryRecordRepository deliveryRepo;
    private PurchaseOrderRecordRepository poRepo;

    // No-arg constructor to support injection by frameworks / Mockito
    public DeliveryRecordServiceImpl() {}

    @org.springframework.beans.factory.annotation.Autowired
    public DeliveryRecordServiceImpl(DeliveryRecordRepository deliveryRepo,
                                     PurchaseOrderRecordRepository poRepo) {
        this.deliveryRepo = deliveryRepo;
        this.poRepo = poRepo;
        System.out.println("[DEBUG] DeliveryRecordServiceImpl constructed with deliveryRepo=" + (deliveryRepo==null?"null":deliveryRepo.getClass()) + ", poRepo=" + (poRepo==null?"null":poRepo.getClass()));
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setDeliveryRepo(DeliveryRecordRepository deliveryRepo) { this.deliveryRepo = deliveryRepo; }

    @org.springframework.beans.factory.annotation.Autowired
    public void setPoRepo(PurchaseOrderRecordRepository poRepo) { this.poRepo = poRepo; }

    public DeliveryRecord recordDelivery(DeliveryRecord delivery) {

        System.out.println("[DEBUG] recordDelivery called with poId=" + delivery.getPoId() + "; qty=" + delivery.getDeliveredQuantity());
        System.out.println("[DEBUG] poRepo instance class=" + (poRepo==null?"null":poRepo.getClass()));
        System.out.println("[DEBUG] deliveryRepo instance class=" + (deliveryRepo==null?"null":deliveryRepo.getClass()));
        System.out.println("[DEBUG] poRepo.findById result present? " + (poRepo.findById(delivery.getPoId()) != null && poRepo.findById(delivery.getPoId()).isPresent()));

        if (poRepo.findById(delivery.getPoId()).isEmpty()) {
            throw new BadRequestException("Invalid PO id");
        }

        if (delivery.getDeliveredQuantity() < 0) {
            throw new BadRequestException("Delivered quantity must be >=");
        }

        return deliveryRepo.save(delivery);
    }

    public List<DeliveryRecord> getDeliveriesByPO(Long poId) {
        return deliveryRepo.findByPoId(poId);
    }

    public List<DeliveryRecord> getAllDeliveries() {
        return deliveryRepo.findAll();
    }
}

