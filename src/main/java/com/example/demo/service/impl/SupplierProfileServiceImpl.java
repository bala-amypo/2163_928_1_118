// package com.example.demo.service.impl;

// import com.example.demo.exception.ResourceNotFoundException;
// import com.example.demo.model.SupplierProfile;
// import com.example.demo.repository.SupplierProfileRepository;
// import com.example.demo.service.SupplierProfileService;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class SupplierProfileServiceImpl implements SupplierProfileService {

//     private final SupplierProfileRepository repository;

//     public SupplierProfileServiceImpl(SupplierProfileRepository repository) {
//         this.repository = repository;
//     }

//     @Override
//     public SupplierProfile createSupplier(SupplierProfile supplier) {
//         repository.findBySupplierCode(supplier.getSupplierCode())
//                 .ifPresent(s -> { throw new IllegalArgumentException("Supplier code already exists"); });
//         return repository.save(supplier);
//     }

//     @Override
//     public SupplierProfile getSupplierById(Long id) {
//         return repository.findById(id)
//                 .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
//     }

//     @Override
//     public Optional<SupplierProfile> getBySupplierCode(String supplierCode) {
//         return repository.findBySupplierCode(supplierCode);
//     }

//     @Override
//     public List<SupplierProfile> getAllSuppliers() {
//         return repository.findAll();
//     }

//     @Override
//     public SupplierProfile updateSupplierStatus(Long id, boolean active) {
//         SupplierProfile supplier = getSupplierById(id);
//         supplier.setActive(active);
//         return repository.save(supplier);
//     }
// }

package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.SupplierProfileService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierProfileServiceImpl implements SupplierProfileService {

    private final SupplierProfileRepository supplierProfileRepository;

    public SupplierProfileServiceImpl(SupplierProfileRepository supplierProfileRepository) {
        this.supplierProfileRepository = supplierProfileRepository;
    }

    @Override
    public SupplierProfile createSupplier(SupplierProfile supplier) {
        if (supplier.getSupplierCode() != null && 
            supplierProfileRepository.findBySupplierCode(supplier.getSupplierCode()).isPresent()) {
            throw new IllegalArgumentException("Duplicate supplier code");
        }
        return supplierProfileRepository.save(supplier);
    }

    @Override
    public SupplierProfile getSupplierById(Long id) {
        return supplierProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
    }

    @Override
    public Optional<SupplierProfile> getBySupplierCode(String supplierCode) {
        return supplierProfileRepository.findBySupplierCode(supplierCode);
    }

    @Override
    public List<SupplierProfile> getAllSuppliers() {
        return supplierProfileRepository.findAll();
    }

    @Override
    public SupplierProfile updateSupplierStatus(Long id, boolean active) {
        SupplierProfile s = getSupplierById(id);
        s.setActive(active);
        return supplierProfileRepository.save(s);
    }
}
