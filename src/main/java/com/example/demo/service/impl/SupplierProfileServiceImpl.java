package com.example.demo.service.impl;

import com.example.demo.model.SupplierProfile;
import com.example.demo.repository.SupplierProfileRepository;
import com.example.demo.service.SupplierProfileService;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SupplierProfileServiceImpl implements SupplierProfileService {

private final SupplierProfileRepository supplierRepo;

public SupplierProfileServiceImpl(SupplierProfileRepository supplierRepo) {
this.supplierRepo = supplierRepo;
}

@Override
public SupplierProfile createSupplier(SupplierProfile supplier) {
return supplierRepo.save(supplier);
}

@Override
public SupplierProfile getSupplierById(Long id) {
return supplierRepo.findById(id)
.orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
}

@Override
public List<SupplierProfile> getAllSuppliers() {
return supplierRepo.findAll();
}

@Override
public SupplierProfile updateSupplierStatus(Long id, boolean active) {
SupplierProfile supplier = getSupplierById(id);
supplier.setActive(active);
return supplierRepo.save(supplier);
}
}
