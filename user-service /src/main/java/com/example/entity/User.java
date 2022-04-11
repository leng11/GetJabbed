package com.example.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="USERS")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Id
    @Column(nullable =false, unique = true)
    private String ssn;
    
    @Column
    private String address;


    @JsonManagedReference
    @OneToOne(mappedBy = "user")
    private Certificate certificate;

}
