package com.tp1_jonathanlorfils.demo.model;

import com.sun.istack.NotNull;
import jdk.jfr.Enabled;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Data
@Entity
public class Citoyen extends User{

//    @NotNull
//    @Column(unique = true)
    private String numeroAssuranceSocial;
//    @NotNull
    private String nom;
//    @NotNull
    private String prenom;
//    @NotNull
    private String sexe;
//    @NotNull
    private int age;
//    @NotNull
    private String motDePasse;
//    @NotNull
    private String numeroTelephone;
//    @NotNull
    private String villeResidence;
//    @NotNull
    private String typePermis;

    @OneToOne
    private Permis permis;
}
