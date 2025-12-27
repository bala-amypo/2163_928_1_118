// package com.example.demo.model;

// import jakarta.persistence.*;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "delay_score_records", uniqueConstraints = @UniqueConstraint(columnNames = "poId"))
// public class DelayScoreRecord {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     private Long supplierId;
//     private Long poId;
//     private Integer delayDays;
//     private String delaySeverity;
//     private Double score;
//     private LocalDateTime computedAt;

//     @PrePersist
//     public void onCreate() {
//         this.computedAt = LocalDateTime.now();
//     }
// }


package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "delay_score_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelayScoreRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long supplierId;
    private Long poId;
    private Integer delayDays;
    private String delaySeverity; // ON_TIME, MINOR, MODERATE, SEVERE
    private Double score;
    private LocalDateTime computedAt;

    public DelayScoreRecord(Long supplierId, Long poId, Integer delayDays, String delaySeverity, Double score) {
        this.supplierId = supplierId;
        this.poId = poId;
        this.delayDays = delayDays;
        this.delaySeverity = delaySeverity;
        this.score = score;
    }

    @PrePersist
    protected void onCreate() {
        this.computedAt = LocalDateTime.now();
    }
}