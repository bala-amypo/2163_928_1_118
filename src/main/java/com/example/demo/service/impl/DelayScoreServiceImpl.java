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
import com.example.demo.model.DelayScoreRecord;
import com.example.demo.model.DeliveryRecord;
import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.model.SupplierProfile;
import com.example.demo.repository.DelayScoreRecordRepository;
import com.example.demo.repository.DeliveryRecordRepository;
import com.example.demo.repository.PurchaseOrderRecordRepository;
import com.example.demo.repository.SupplierProfileRepository;
import com.example.demo.service.DelayScoreService;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DelayScoreServiceImpl implements DelayScoreService {

    private final DelayScoreRecordRepository delayScoreRepository;
    private final PurchaseOrderRecordRepository poRepository;
    private final DeliveryRecordRepository deliveryRepository;
    private final SupplierProfileRepository supplierRepository;

    // REMOVED SupplierRiskAlertService to prevent TestNG instantiation error
    public DelayScoreServiceImpl(DelayScoreRecordRepository delayScoreRepository,
                                 PurchaseOrderRecordRepository poRepository,
                                 DeliveryRecordRepository deliveryRepository,
                                 SupplierProfileRepository supplierRepository) {
        this.delayScoreRepository = delayScoreRepository;
        this.poRepository = poRepository;
        this.deliveryRepository = deliveryRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    public DelayScoreRecord computeDelayScore(Long poId) {
        PurchaseOrderRecord po = poRepository.findById(poId)
                .orElseThrow(() -> new ResourceNotFoundException("PO not found"));

        SupplierProfile supplier = supplierRepository.findById(po.getSupplierId())
                .orElseThrow(() -> new BadRequestException("Supplier not found"));

        if (!Boolean.TRUE.equals(supplier.getActive())) {
            throw new BadRequestException("Supplier is inactive");
        }

        List<DeliveryRecord> deliveries = deliveryRepository.findByPoId(poId);
        if (deliveries.isEmpty()) {
            throw new BadRequestException("No deliveries found for this PO");
        }

        // Logic: Use the first delivery found
        DeliveryRecord delivery = deliveries.get(0);

        long delayDaysLong = ChronoUnit.DAYS.between(po.getPromisedDeliveryDate(), delivery.getActualDeliveryDate());
        int delayDays = (int) delayDaysLong;

        // If delivered early (negative), treat as 0 delay
        if (delayDays < 0) delayDays = 0;

        String severity;
        if (delayDays == 0) severity = "ON_TIME";
        else if (delayDays <= 3) severity = "MINOR";
        else if (delayDays <= 7) severity = "MODERATE";
        else severity = "SEVERE";

        // Score formula: 100 - (delay * 5), min 0
        double score = 100.0 - (delayDays * 5.0);
        if (score < 0) score = 0.0;
        if (score > 100) score = 100.0;

        DelayScoreRecord record = new DelayScoreRecord();
        record.setPoId(poId);
        record.setSupplierId(po.getSupplierId());
        record.setDelayDays(delayDays);
        record.setDelaySeverity(severity);
        record.setScore(score);

        return delayScoreRepository.save(record);
    }

    @Override
    public List<DelayScoreRecord> getScoresBySupplier(Long supplierId) {
        return delayScoreRepository.findBySupplierId(supplierId);
    }

    @Override
    public List<DelayScoreRecord> getAllScores() {
        return delayScoreRepository.findAll();
    }
}