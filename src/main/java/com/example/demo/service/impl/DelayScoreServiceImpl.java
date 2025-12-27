// package com.example.demo.service.impl;

// import com.example.demo.exception.BadRequestException;
// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.model.*;
// import com.example.demo.repository.*;
// import com.example.demo.service.DelayScoreService;
// import com.example.demo.service.SupplierRiskAlertService;
// import org.springframework.stereotype.Service;

// import java.time.temporal.ChronoUnit;
// import java.util.List;
// import java.util.Optional;

// @Service
// public class DelayScoreServiceImpl implements DelayScoreService {

//     private final DelayScoreRecordRepository scoreRepository;
//     private final PurchaseOrderRecordRepository poRepository;
//     private final DeliveryRecordRepository deliveryRepository;
//     private final SupplierProfileRepository supplierRepository;
//     private final SupplierRiskAlertService riskAlertService;

//     public DelayScoreServiceImpl(DelayScoreRecordRepository scoreRepository,
//                                  PurchaseOrderRecordRepository poRepository,
//                                  DeliveryRecordRepository deliveryRepository,
//                                  SupplierProfileRepository supplierRepository,
//                                  SupplierRiskAlertService riskAlertService) {
//         this.scoreRepository = scoreRepository;
//         this.poRepository = poRepository;
//         this.deliveryRepository = deliveryRepository;
//         this.supplierRepository = supplierRepository;
//         this.riskAlertService = riskAlertService;
//     }

//     @Override
//     public DelayScoreRecord computeDelayScore(Long poId) {

//         PurchaseOrderRecord po = poRepository.findById(poId)
//                 .orElseThrow(() -> new ResourceNotFoundException("Purchase order not found"));

//         SupplierProfile supplier = supplierRepository.findById(po.getSupplierId())
//                 .orElseThrow(() -> new BadRequestException("Invalid supplierId"));

//         if (!supplier.getActive()) {
//             throw new BadRequestException("Inactive supplier");
//         }

//         List<DeliveryRecord> deliveries = deliveryRepository.findByPoId(poId);

//         if (deliveries.isEmpty()) {
//             throw new BadRequestException("No deliveries");
//         }

//         DeliveryRecord latest = deliveries.get(deliveries.size() - 1);
//         long delayDays = ChronoUnit.DAYS.between(po.getPromisedDeliveryDate(), latest.getActualDeliveryDate());

//         String severity;
//         double score;

//         if (delayDays <= 0) {
//             severity = "ON_TIME";
//             score = 100;
//         } else if (delayDays <= 3) {
//             severity = "MINOR";
//             score = 75;
//         } else if (delayDays <= 7) {
//             severity = "MODERATE";
//             score = 50;
//         } else {
//             severity = "SEVERE";
//             score = 0;
//         }

//         DelayScoreRecord record = new DelayScoreRecord();
//         record.setSupplierId(po.getSupplierId());
//         record.setPoId(poId);
//         record.setDelayDays((int) delayDays);
//         record.setDelaySeverity(severity);
//         record.setScore(score);

//         DelayScoreRecord saved = scoreRepository.save(record);

//         if ("SEVERE".equals(severity)) {
//             SupplierRiskAlert alert = new SupplierRiskAlert();
//             alert.setSupplierId(po.getSupplierId());
//             alert.setAlertLevel("HIGH");
//             alert.setMessage("Severe delivery delay detected");
//             riskAlertService.createAlert(alert);
//         }

//         return saved;
//     }

//     @Override
//     public List<DelayScoreRecord> getScoresBySupplier(Long supplierId) {
//         return scoreRepository.findBySupplierId(supplierId);
//     }

//     @Override
//     public Optional<DelayScoreRecord> getScoreById(Long id) {
//         return scoreRepository.findById(id);
//     }

//     @Override
//     public List<DelayScoreRecord> getAllScores() {
//         return scoreRepository.findAll();
//     }
// }


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

    private final DelayScoreRecordRepository delayScoreRecordRepository;
    private final PurchaseOrderRecordRepository poRepository;
    private final DeliveryRecordRepository deliveryRepository;
    private final SupplierProfileRepository supplierProfileRepository;
    private final SupplierRiskAlertService supplierRiskAlertService; // Required by constructor in test

    public DelayScoreServiceImpl(DelayScoreRecordRepository delayScoreRecordRepository,
                                 PurchaseOrderRecordRepository poRepository,
                                 DeliveryRecordRepository deliveryRepository,
                                 SupplierProfileRepository supplierProfileRepository,
                                 SupplierRiskAlertService supplierRiskAlertService) {
        this.delayScoreRecordRepository = delayScoreRecordRepository;
        this.poRepository = poRepository;
        this.deliveryRepository = deliveryRepository;
        this.supplierProfileRepository = supplierProfileRepository;
        this.supplierRiskAlertService = supplierRiskAlertService;
    }

    @Override
    public DelayScoreRecord computeDelayScore(Long poId) {
        PurchaseOrderRecord po = poRepository.findById(poId)
                .orElseThrow(() -> new ResourceNotFoundException("PO not found"));

        SupplierProfile supplier = supplierProfileRepository.findById(po.getSupplierId())
                .orElseThrow(() -> new BadRequestException("Invalid supplier"));

        if (!Boolean.TRUE.equals(supplier.getActive())) {
            throw new BadRequestException("Inactive supplier");
        }

        List<DeliveryRecord> deliveries = deliveryRepository.findByPoId(poId);
        if (deliveries.isEmpty()) {
            throw new BadRequestException("No deliveries");
        }

        // Logic based on first delivery found
        DeliveryRecord delivery = deliveries.get(0);
        long delayDaysLong = ChronoUnit.DAYS.between(po.getPromisedDeliveryDate(), delivery.getActualDeliveryDate());
        int delayDays = (int) delayDaysLong;

        // Logic: if early (negative delay), treat as 0 delay (ON_TIME)
        if (delayDays < 0) {
            delayDays = 0;
        }

        String severity;
        if (delayDays == 0) severity = "ON_TIME";
        else if (delayDays <= 3) severity = "MINOR";
        else if (delayDays <= 7) severity = "MODERATE";
        else severity = "SEVERE";

        double score = 100.0 - (delayDays * 5.0);
        if (score < 0) score = 0.0;
        if (score > 100) score = 100.0;

        DelayScoreRecord record = new DelayScoreRecord();
        record.setPoId(poId);
        record.setSupplierId(po.getSupplierId());
        record.setDelayDays(delayDays);
        record.setDelaySeverity(severity);
        record.setScore(score);

        return delayScoreRecordRepository.save(record);
    }

    @Override
    public List<DelayScoreRecord> getScoresBySupplier(Long supplierId) {
        return delayScoreRecordRepository.findBySupplierId(supplierId);
    }

    @Override
    public List<DelayScoreRecord> getAllScores() {
        return delayScoreRecordRepository.findAll();
    }
}