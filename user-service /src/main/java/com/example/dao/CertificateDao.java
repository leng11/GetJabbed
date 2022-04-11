package com.example.dao;

import com.example.entity.Certificate;
import org.springframework.data.repository.CrudRepository;

public interface CertificateDao extends CrudRepository<Certificate,Integer> {
    public Certificate getCertificateByUserSsn(final String ssn);
}
