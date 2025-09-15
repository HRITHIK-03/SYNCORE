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
    private final com.example.syncore.service.MicroserviceSimulator sim;

    public PatientController(PatientService svc, com.example.syncore.service.MicroserviceSimulator sim) {
        this.svc = svc;
        this.sim = sim;
    }

    @GetMapping
    public List<Patient> list() { return svc.findAll(); }

    @PostMapping
    public Patient create(@RequestBody Patient p) { return svc.create(p); }

    @GetMapping("/simulate/{input}")
    public String simulate(@PathVariable String input) { return sim.callAnotherService(input); }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> get(@PathVariable String id) {
        return svc.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new com.example.syncore.exception.NotFoundException("Patient not found: " + id));
    }
}
