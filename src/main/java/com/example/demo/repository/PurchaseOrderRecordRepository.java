package com.example.demo.repository;

import com.example.demo.model.PurchaseOrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseOrderRecordRepository
        extends JpaRepository<PurchaseOrderRecord, Long> {

    List<PurchaseOrderRecord> findBySupplierId(Long supplierId);

    List<PurchaseOrderRecord> findByIssuedDateBetween(
            LocalDateTime start, LocalDateTime end
    );
}

