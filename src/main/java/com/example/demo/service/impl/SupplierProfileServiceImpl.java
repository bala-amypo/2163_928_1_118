package com.example.demo.service.impl;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.SupplierProfile;
import com.example.demo.repository.SupplierProfileRepository;
import com.example.demo.service.SupplierProfileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierProfileServiceImpl implements SupplierProfileService {

    private final SupplierProfileRepository supplierRepository;

    public SupplierProfileServiceImpl(SupplierProfileRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public SupplierProfile createSupplier(SupplierProfile supplier) {
        supplierRepository.findBySupplierCode(supplier.getSupplierCode())
                .ifPresent(s -> {
                    throw new IllegalArgumentException("Supplier code already exists");
                });
        return supplierRepository.save(supplier);
    }

    @Override
    public SupplierProfile getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Supplier not found"));
    }

    @Override
    public Optional<SupplierProfile> getBySupplierCode(String supplierCode) {
        return supplierRepository.findBySupplierCode(supplierCode);
    }

    @Override
    public List<SupplierProfile> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public SupplierProfile updateSupplierStatus(Long id, boolean active) {
        SupplierProfile supplier = getSupplierById(id);
        supplier.setActive(active);
        return supplierRepository.save(supplier);
    }
}
