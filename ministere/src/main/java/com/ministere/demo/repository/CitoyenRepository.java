package com.ministere.demo.repository;

import com.ministere.demo.model.Citoyen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitoyenRepository extends JpaRepository<Citoyen, Integer> {

    Citoyen findCitoyenByNassm(String Nassm);
}
