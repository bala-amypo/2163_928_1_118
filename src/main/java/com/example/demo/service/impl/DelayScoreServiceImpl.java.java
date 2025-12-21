package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DelayScoreService;
import com.example.demo.service.SupplierRiskAlertService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DelayScoreServiceImpl implements DelayScoreService {

    private final DelayScoreRecordRepository scoreRepository;
    private final PurchaseOrderRecordRepository poRepository;
    private final DeliveryRecordRepository deliveryRepository;
    private final SupplierProfileRepository supplierRepository;
    private final SupplierRiskAlertService alertService;

    public DelayScoreServiceImpl(
            DelayScoreRecordRepository scoreRepository,
            PurchaseOrderRecordRepository poRepository,
            DeliveryRecordRepository deliveryRepository,
            SupplierProfileRepository supplierRepository,
            SupplierRiskAlertService alertService) {

        this.scoreRepository = scoreRepository;
        this.poRepository = poRepository;
        this.deliveryRepository = deliveryRepository;
        this.supplierRepository = supplierRepository;
        this.alertService = alertService;
    }

    @Override
    public DelayScoreRecord computeDelayScore(Long poId) {

        PurchaseOrderRecord po = poRepository.findById(poId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Purchase order not found"));

        SupplierProfile supplier = supplierRepository.findById(po.getSupplierId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found"));

        if (!supplier.getActive()) {
            throw new BadRequestException("Inactive supplier");
        }

        List<DeliveryRecord> deliveries = deliveryRepository.findByPoId(poId);

        if (deliveries.isEmpty()) {
            throw new BadRequestException("No deliveries");
        }

        DeliveryRecord delivery = deliveries.get(deliveries.size() - 1);

        long delayDays = ChronoUnit.DAYS.between(
                po.getPromisedDeliveryDate(),
                delivery.getActualDeliveryDate()
        );

        int delay = (int) Math.max(delayDays, 0);

        String severity;
        if (delay == 0) severity = "ON_TIME";
        else if (delay <= 3) severity = "MINOR";
        else if (delay <= 7) severity = "MODERATE";
        else severity = "SEVERE";

        double score = Math.max(100 - (delay * 5), 0);

        DelayScoreRecord record = new DelayScoreRecord(
                supplier.getId(),
                poId,
                delay,
                severity,
                score
        );

        DelayScoreRecord saved = scoreRepository.save(record);

        if ("SEVERE".equals(severity)) {
            alertService.createAlert(
                    new SupplierRiskAlert(
                            supplier.getId(),
                            "HIGH",
                            "Severe delivery delay detected"
                    )
            );
        }

        return saved;
    }

    @Override
    public List<DelayScoreRecord> getScoresBySupplier(Long supplierId) {
        return scoreRepository.findBySupplierId(supplierId);
    }

    @Override
    public List<DelayScoreRecord> getAllScores() {
        return scoreRepository.findAll();
    }
}
