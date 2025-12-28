
package com.example.demo.repository;

import com.example.demo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;


public interface DelayScoreRecordRepository extends JpaRepository<DelayScoreRecord, Long> {
    Optional<DelayScoreRecord> findByPoId(Long poId);
    List<DelayScoreRecord> findBySupplierId(Long supplierId);
}
