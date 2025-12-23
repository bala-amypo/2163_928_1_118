package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "supplier_risk_alerts")
public class SupplierRiskAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long supplierId;

    private String alertLevel;   // LOW, MEDIUM, HIGH

    private String message;

    private LocalDateTime alertDate;

    private Boolean resolved = false;

    public SupplierRiskAlert() {
    }

    public SupplierRiskAlert(Long supplierId, String alertLevel, String message) {
        this.supplierId = supplierId;
        this.alertLevel = alertLevel;
        this.message = message;
    }

    @PrePersist
    public void onCreate() {
        this.alertDate = LocalDateTime.now();
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getAlertLevel() {
        return alertLevel;
    }

    public void setAlertLevel(String alertLevel) {
        this.alertLevel = alertLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getAlertDate() {
        return alertDate;
    }

    public Boolean getResolved() {
        return resolved;
    }

    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }
}
