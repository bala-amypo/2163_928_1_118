// package com.example.demo.repository;

// import com.example.demo.model.DeliveryRecord;
// import org.springframework.data.jpa.repository.JpaRepository;

// import java.util.List;

// public interface DeliveryRecordRepository extends JpaRepository<DeliveryRecord, Long> {
//     List<DeliveryRecord> findByPoId(Long poId);
// }

package com.example.demo.repository;

import com.example.demo.model.DeliveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DeliveryRecordRepository extends JpaRepository<DeliveryRecord, Long> {
    List<DeliveryRecord> findByPoId(Long poId);
}