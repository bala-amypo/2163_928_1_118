package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "purchase_order_records", uniqueConstraints = @UniqueConstraint(columnNames = "poNumber"))
public class PurchaseOrderRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String poNumber;
    private Long supplierId;
    private String itemDescription;
    private Integer quantity;
    private LocalDate promisedDeliveryDate;
    private LocalDate issuedDate;

    public PurchaseOrderRecord() {}

    public PurchaseOrderRecord(String poNumber, Long supplierId, String itemDescription,
                               Integer quantity, LocalDate promisedDeliveryDate, LocalDate issuedDate) {
        this.poNumber = poNumber;
        this.supplierId = supplierId;
        this.itemDescription = itemDescription;
        this.quantity = quantity;
        this.promisedDeliveryDate = promisedDeliveryDate;
        this.issuedDate = issuedDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPoNumber() { return poNumber; }
    public void setPoNumber(String poNumber) { this.poNumber = poNumber; }

    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public LocalDate getPromisedDeliveryDate() { return promisedDeliveryDate; }
    public void setPromisedDeliveryDate(LocalDate promisedDeliveryDate) {
        this.promisedDeliveryDate = promisedDeliveryDate;
    }

    public LocalDate getIssuedDate() { return issuedDate; }
    public void setIssuedDate(LocalDate issuedDate) { this.issuedDate = issuedDate; }
}
