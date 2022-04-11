package com.example.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Table(name="CERTIFICATE")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private int DosesCount;

    @Column(nullable = false)
    private String location;

    @JsonBackReference
    @JoinColumn(name="ssn")
    @OneToOne
    private User user;

}
