package com.tp1_jonathanlorfils.demo.model;


import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Administrateur extends User {

    private String login;
}
