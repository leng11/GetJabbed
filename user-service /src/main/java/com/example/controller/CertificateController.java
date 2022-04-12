package com.example.controller;

import com.example.dao.CertificateDao;
import com.example.entity.Certificate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/certificate")
public class CertificateController {

    private final CertificateDao certificateDao;


    public CertificateController(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @GetMapping("/generate")
    public Certificate getCertificateByUser(@RequestParam(value = "official_id") final String official_id){
        return certificateDao.getCertificateById(official_id);
    }

    @PostMapping("/add")
    public Certificate saveCertificate(@RequestBody Certificate certificate){
        return certificateDao.save(certificate);
    }

}
