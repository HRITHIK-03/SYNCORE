package com.example.syncore.controller;

import com.example.syncore.model.Patient;
import com.example.syncore.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService svc;

    public PatientController(PatientService svc) { this.svc = svc; }

    @GetMapping
    public List<Patient> list() { return svc.findAll(); }

    @PostMapping
    public Patient create(@RequestBody Patient p) { return svc.create(p); }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> get(@PathVariable String id) {
        return svc.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
