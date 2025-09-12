package com.example.syncore.service;

import com.example.syncore.model.Patient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PatientService {
    private final Map<String, Patient> store = new ConcurrentHashMap<>();

    public PatientService() {
        // seed data
        Patient p = new Patient("p1", "Asha Kumar", "asha.kumar@example.com", "9876543210", "Hypertension");
        store.put(p.getId(), p);
    }

    public Patient create(Patient p) {
        if (p.getId() == null || p.getId().isEmpty()) {
            p.setId(UUID.randomUUID().toString());
        }
        store.put(p.getId(), p);
        return p;
    }

    @Cacheable(value = "patients", key = "#id")
    public Optional<Patient> findById(String id) {
        // simulate latency for demo
        try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        return Optional.ofNullable(store.get(id));
    }

    public List<Patient> findAll() {
        return new ArrayList<>(store.values());
    }
}
