package com.tp1_jonathanlorfils.demo.repository;

import com.tp1_jonathanlorfils.demo.model.Citoyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitoyenRepository extends JpaRepository<Citoyen, Integer> {

    public Citoyen findCitoyenByCourriel(String courriel);

    public Citoyen findCitoyenByCourrielAndMotDePasse(String courriel, String mdp);

    public Citoyen findCitoyenByNumeroAssuranceSocial(String numeroAssuranceSocial);

    public Citoyen findCitoyenByNomAndPrenomAndNumeroTelephone(String nom, String prenom, String numeroTelephone);

    public Citoyen findCitoyenByCourrielAndNumeroTelephone(String courriel, String numeroTelephone);

}
