package com.example.demo.repository;

import com.example.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface SupplierProfileRepository extends JpaRepository<SupplierProfile, Long> {
    Optional<SupplierProfile> findBySupplierCode(String supplierCode);
    List<SupplierProfile> findBySupplierNameContainingIgnoreCase(String name);
    

}
