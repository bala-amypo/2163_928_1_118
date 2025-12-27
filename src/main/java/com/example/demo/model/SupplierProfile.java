// package com.example.demo.model;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "supplier_profiles", uniqueConstraints = @UniqueConstraint(columnNames = "supplierCode"))
// public class SupplierProfile {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private String supplierCode;
//     private String supplierName;
//     private String email;
//     private String phone;
//     private Boolean active = true;
//     private LocalDateTime createdAt;

//     public SupplierProfile() {}

//     @PrePersist
//     public void onCreate() {
//         this.createdAt = LocalDateTime.now();
//     }
// }


package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "supplier_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String supplierCode;

    private String supplierName;
    private String email;
    private String phone;
    
    private Boolean active = true;
    private LocalDateTime createdAt;

    public SupplierProfile(String supplierCode, String supplierName, String email, String phone, Boolean active) {
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.email = email;
        this.phone = phone;
        this.active = active != null ? active : true;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.active == null) this.active = true;
    }
}