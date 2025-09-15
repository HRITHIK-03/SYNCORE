package com.example.syncore.controller;

import com.example.syncore.model.Patient;
import com.example.syncore.service.MicroserviceSimulator;
import com.example.syncore.service.PatientService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@WebMvcTest(controllers = PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @MockBean
    private MicroserviceSimulator sim;

    @Test
    public void testGetExistingPatient() throws Exception {
        Patient p = new Patient("p1","Test","t@test.com","999","Check");
        Mockito.when(patientService.findById("p1")).thenReturn(Optional.of(p));

        mockMvc.perform(get("/api/patients/p1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("p1")))
                .andExpect(jsonPath("$.name", is("Test")));
    }

    @Test
    public void testCreatePatient() throws Exception {
        String body = "{\"name\":\"New\",\"email\":\"n@e.com\",\"phone\":\"123\",\"diagnosis\":\"NA\"}";
        Patient created = new Patient("p2","New","n@e.com","123","NA");
        Mockito.when(patientService.create(Mockito.any())).thenReturn(created);

        mockMvc.perform(post("/api/patients").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("p2")))
                .andExpect(jsonPath("$.name", is("New")));
    }
}
