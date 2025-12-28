package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.PurchaseOrderRecord;
import com.example.demo.model.SupplierProfile;
import com.example.demo.repository.PurchaseOrderRecordRepository;
import com.example.demo.repository.SupplierProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderServiceImpl {

    private PurchaseOrderRecordRepository poRepo;
    private SupplierProfileRepository supplierRepo;

    // No-arg constructor to support injection by frameworks / Mockito
    public PurchaseOrderServiceImpl() {}

    @org.springframework.beans.factory.annotation.Autowired
    public PurchaseOrderServiceImpl(PurchaseOrderRecordRepository poRepo,
                                    SupplierProfileRepository supplierRepo) {
        this.poRepo = poRepo;
        this.supplierRepo = supplierRepo;
        System.out.println("[DEBUG] PurchaseOrderServiceImpl constructed with poRepo=" + (poRepo==null?"null":poRepo.getClass()) + ", supplierRepo=" + (supplierRepo==null?"null":supplierRepo.getClass()));
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setPoRepo(PurchaseOrderRecordRepository poRepo) { this.poRepo = poRepo; }

    @org.springframework.beans.factory.annotation.Autowired
    public void setSupplierRepo(SupplierProfileRepository supplierRepo) { this.supplierRepo = supplierRepo; }

    public PurchaseOrderRecord createPurchaseOrder(PurchaseOrderRecord po) {

        System.out.println("[DEBUG] createPurchaseOrder called with supplierId=" + po.getSupplierId());
        System.out.println("[DEBUG] supplierRepo instance class=" + (supplierRepo==null?"null":supplierRepo.getClass()));
        System.out.println("[DEBUG] poRepo instance class=" + (poRepo==null?"null":poRepo.getClass()));
        Optional<SupplierProfile> supplierOpt =
                supplierRepo.findById(po.getSupplierId());
        System.out.println("[DEBUG] supplierOpt present? " + (supplierOpt != null && supplierOpt.isPresent()));

        if (supplierOpt.isEmpty()) {
            throw new BadRequestException("Invalid supplierId");
        }

        if (!Boolean.TRUE.equals(supplierOpt.get().getActive())) {
            throw new BadRequestException("Supplier must be active");
        }

        return poRepo.save(po);
    }

    public List<PurchaseOrderRecord> getPOsBySupplier(Long supplierId) {
        return poRepo.findBySupplierId(supplierId);
    }

    public Optional<PurchaseOrderRecord> getPOById(Long id) {
        return poRepo.findById(id);
    }

    public List<PurchaseOrderRecord> getAllPurchaseOrders() {
        return poRepo.findAll();
    }
}

