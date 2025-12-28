package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.SupplierProfile;
import com.example.demo.repository.SupplierProfileRepository;
import com.example.demo.service.SupplierProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierProfileServiceImpl implements SupplierProfileService {

    @Autowired
    private SupplierProfileRepository supplierProfileRepository;
    @Override
   public SupplierProfile getSupplierById(Long id) {
    return supplierProfileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Supplier not found"));
}


    @Override
    public List<SupplierProfile> getAllSuppliers() {
        return supplierProfileRepository.findAll();
    }

    @Override
    public SupplierProfile createSupplier(SupplierProfile supplier) {
        return supplierProfileRepository.save(supplier);
    }

    @Override
    public SupplierProfile updateSupplierStatus(Long id, Boolean active) {
        SupplierProfile supplier = getSupplierById(id);
        supplier.setActive(active);
        return supplierProfileRepository.save(supplier);
    }

    @Override
    public Optional<SupplierProfile> getBySupplierCode(String supplierCode) {
        return supplierProfileRepository.findBySupplierCode(supplierCode);
    }
}


