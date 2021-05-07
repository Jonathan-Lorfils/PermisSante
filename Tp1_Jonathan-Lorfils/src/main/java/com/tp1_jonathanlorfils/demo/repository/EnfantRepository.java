package com.tp1_jonathanlorfils.demo.repository;

import com.tp1_jonathanlorfils.demo.model.Citoyen;
import com.tp1_jonathanlorfils.demo.model.Enfant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnfantRepository extends JpaRepository<Enfant, Integer> {

    public Enfant findEnfantByTuteur(Citoyen tuteur);


}
