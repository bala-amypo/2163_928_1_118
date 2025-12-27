// package com.example.demo.model;

// import jakarta.persistence.*;
// import java.time.LocalDate;

// @Entity
// @Table(name = "delivery_records")
// public class DeliveryRecord {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private Long poId;
//     private LocalDate actualDeliveryDate;
//     private Integer deliveredQuantity;
//     private String notes;

//     public DeliveryRecord() {}
// }

package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "delivery_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long poId;
    private LocalDate actualDeliveryDate;
    private Integer deliveredQuantity;
    private String notes;

    public DeliveryRecord(Long poId, LocalDate actualDeliveryDate, Integer deliveredQuantity, String notes) {
        this.poId = poId;
        this.actualDeliveryDate = actualDeliveryDate;
        this.deliveredQuantity = deliveredQuantity;
        this.notes = notes;
    }
}