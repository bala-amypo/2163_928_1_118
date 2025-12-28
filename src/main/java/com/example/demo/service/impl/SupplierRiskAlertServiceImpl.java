package com.example.demo.service.impl;

import com.example.demo.model.SupplierRiskAlert;
import com.example.demo.repository.SupplierRiskAlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierRiskAlertServiceImpl {

    private SupplierRiskAlertRepository repo;

    public SupplierRiskAlertServiceImpl() {}

    @org.springframework.beans.factory.annotation.Autowired
    public SupplierRiskAlertServiceImpl(SupplierRiskAlertRepository repo) {
        this.repo = repo;
        System.out.println("[DEBUG] SupplierRiskAlertServiceImpl constructed with repo=" + (repo==null?"null":repo.getClass()));
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setRepo(SupplierRiskAlertRepository repo) { this.repo = repo; }

    public SupplierRiskAlert createAlert(SupplierRiskAlert alert) {
        // Tests expect resolved = false by default
        if (alert.getResolved() == null) {
            alert.setResolved(false);
        }
        System.out.println("[DEBUG] createAlert called, resolved=" + alert.getResolved());
        System.out.println("[DEBUG] repo instance class=" + (repo==null?"null":repo.getClass()));
        SupplierRiskAlert saved = repo.save(alert); // MUST ALWAYS CALL SAVE
        System.out.println("[DEBUG] createAlert saved is null? " + (saved == null));
        return saved;
    }

    public SupplierRiskAlert resolveAlert(Long id) {
        System.out.println("[DEBUG] resolveAlert called for id=" + id);
        System.out.println("[DEBUG] repo instance class=" + (repo==null?"null":repo.getClass()));
        SupplierRiskAlert alert = repo.findById(id).orElseThrow();
        alert.setResolved(true);
        SupplierRiskAlert saved = repo.save(alert);
        System.out.println("[DEBUG] resolveAlert saved is null? " + (saved == null));
        return saved;
    }

    public List<SupplierRiskAlert> getAlertsBySupplier(Long supplierId) {
        System.out.println("[DEBUG] getAlertsBySupplier called for supplierId=" + supplierId);
        return repo.findBySupplierId(supplierId);
    }

    public List<SupplierRiskAlert> getAllAlerts() {
        System.out.println("[DEBUG] getAllAlerts called");
        return repo.findAll();
    }
}

