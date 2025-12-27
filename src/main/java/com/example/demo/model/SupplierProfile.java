package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "supplier_profiles", uniqueConstraints = @UniqueConstraint(columnNames = "supplierCode"))
public class SupplierProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String supplierCode;
    private String supplierName;
    private String email;
    private String phone;
    private Boolean active = true;

    private LocalDateTime createdAt;

    public SupplierProfile() {}

    public SupplierProfile(String supplierCode, String supplierName, String email, String phone, Boolean active) {
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.email = email;
        this.phone = phone;
        this.active = active;
    }

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        if (active == null) active = true;
    }

    public Long getId() { return id; }
    public String getSupplierCode() { return supplierCode; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
