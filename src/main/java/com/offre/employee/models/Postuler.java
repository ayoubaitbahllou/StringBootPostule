package com.offre.employee.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "postuler")
public class Postuler {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
}
