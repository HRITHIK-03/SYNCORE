package com.example.syncore;

import com.example.syncore.model.Patient;
import com.example.syncore.service.PatientService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatientServiceTest {

    @Test
    public void createAndFind() {
        PatientService svc = new PatientService();
        Patient p = new Patient(null, "Test", "t@test.com", "9999999999", "Checkup");
        Patient created = svc.create(p);
        assertNotNull(created.getId());
        assertTrue(svc.findById(created.getId()).isPresent());
    }
}
