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

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
