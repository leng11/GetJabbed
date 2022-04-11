package com.example.controller;

import com.example.dao.CertificateDao;
import com.example.entity.Certificate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/certificate-controller")
public class CertificateController {

    private final CertificateDao certificateDao;


    public CertificateController(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @GetMapping("/user/certificate")
    public Certificate getCertificateByUser(@RequestParam(value = "ssn") final String ssn){
        return certificateDao.getCertificateByUserSsn(ssn);
    }

    @PostMapping("/add")
    public Certificate saveCertificate(@RequestBody Certificate certificate){
        return certificateDao.save(certificate);
    }

}
