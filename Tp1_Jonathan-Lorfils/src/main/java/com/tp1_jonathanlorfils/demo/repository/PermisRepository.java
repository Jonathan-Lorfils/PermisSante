package com.tp1_jonathanlorfils.demo.repository;

import com.tp1_jonathanlorfils.demo.model.Permis;
import com.tp1_jonathanlorfils.demo.model.PermisTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermisRepository extends JpaRepository<Permis, Integer> {

    public List<Permis> findPermisByTypePermis(String typePermis);

    public Permis findPermisByIdPermis(int id);
}