package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SupplierProfile {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(unique = true, nullable = false)
private String supplierCode;

private String supplierName;
private String email;
private String phone;
private Boolean active;
private LocalDateTime createdAt;

@PrePersist
public void onCreate() {
this.createdAt = LocalDateTime.now();
this.active = true;
}

public Long getId() { return id; }
public String getSupplierCode() { return supplierCode; }
public void setSupplierCode(String supplierCode) { this.supplierCode = supplierCode; }
public Boolean getActive() { return active; }
public void setActive(Boolean active) { this.active = active; }
}
