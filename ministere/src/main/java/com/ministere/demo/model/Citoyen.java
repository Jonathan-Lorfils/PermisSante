package com.ministere.demo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Citoyen {
    @Id
    private Integer id;

    private String name;
    private String nassm;
    private boolean valid;
    private String type;
}
