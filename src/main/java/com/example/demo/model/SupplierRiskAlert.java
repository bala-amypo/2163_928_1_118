// package com.example.demo.model;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "supplier_risk_alerts")
// public class SupplierRiskAlert {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private Long supplierId;
//     private String alertLevel;
//     private String message;
//     private Boolean resolved = false;
//     private LocalDateTime alertDate;

//     @PrePersist
//     public void onCreate() {
//         this.alertDate = LocalDateTime.now();
//     }
// }


package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "supplier_risk_alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRiskAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long supplierId;
    private String alertLevel; // LOW, MEDIUM, HIGH
    private String message;
    private LocalDateTime alertDate;
    private Boolean resolved = false;

    public SupplierRiskAlert(Long supplierId, String alertLevel, String message) {
        this.supplierId = supplierId;
        this.alertLevel = alertLevel;
        this.message = message;
        this.resolved = false;
    }

    @PrePersist
    protected void onCreate() {
        this.alertDate = LocalDateTime.now();
        if (this.resolved == null) this.resolved = false;
    }
}