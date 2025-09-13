package com.example.syncore.service;

import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class FTPPoller {
    private static final Logger log = LoggerFactory.getLogger(FTPPoller.class);

    @Value("${syncore.ftp.host:ftp}") private String host;
    @Value("${syncore.ftp.port:21}") private int port;
    @Value("${syncore.ftp.user:demo}") private String user;
    @Value("${syncore.ftp.pass:demo}") private String pass;

    private final EmailService emailService;
    private final PatientService patientService;

    public FTPPoller(EmailService emailService, PatientService patientService) {
        this.emailService = emailService;
        this.patientService = patientService;
    }

    // every 30 seconds for demo
    @Scheduled(fixedDelayString = "${syncore.ftp.poll-interval-ms:30000}")
    public void poll() {
        log.info("Starting FTP poll to {}:{}", host, port);
        FTPClient client = new FTPClient();
        List<String> processed = new ArrayList<>();
        List<String> failed = new ArrayList<>();
        try {
            client.connect(host, port);
            boolean login = client.login(user, pass);
            client.enterLocalPassiveMode();
            if (!login) {
                log.error("FTP login failed for {}", host);
                emailService.sendReport("FTP Poll - failed login", "Unable to login to FTP server");
                return;
            }
            String[] files = client.listNames();
            if (files == null) files = new String[0];
            for (String file : files) {
                if (file.toLowerCase().endsWith(".csv")) {
                    log.info("Found CSV: {}", file);
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(client.retrieveFileStream(file), StandardCharsets.UTF_8))) {
                        String line;
                        int count = 0;
                        while ((line = br.readLine()) != null) {
                            // simple CSV: name,email,phone,diagnosis
                            String[] parts = line.split(",");
                            if (parts.length >= 4) {
                                com.example.syncore.model.Patient p = new com.example.syncore.model.Patient(null, parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim());
                                patientService.create(p);
                                count++;
                            }
                        }
                        client.completePendingCommand();
                        processed.add(file + " (" + count + " records)"); 
                    } catch (Exception ex) {
                        log.error("Failed processing {}: {}", file, ex.getMessage());
                        failed.add(file + ": " + ex.getMessage());
                    }
                }
            }
            StringBuilder body = new StringBuilder();
            body.append("Processed files:\n");
            for (String s: processed) body.append(s).append("\n");
            if (!failed.isEmpty()) {
                body.append("\nFailed files:\n");
                for (String s: failed) body.append(s).append("\n");
            }
            emailService.sendReport("FTP Poll Report", body.toString());
        } catch (Exception ex) {
            log.error("FTP poll failed: {}", ex.getMessage());
            emailService.sendReport("FTP Poll - Error", ex.getMessage());
        } finally {
            try { client.logout(); client.disconnect(); } catch (Exception ignored) {}
        }
    }
}
