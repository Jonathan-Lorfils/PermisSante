package com.tp1_jonathanlorfils.demo;

import com.tp1_jonathanlorfils.demo.Service.SystemService;
import com.tp1_jonathanlorfils.demo.model.*;
import com.tp1_jonathanlorfils.demo.repository.CitoyenRepository;
import com.tp1_jonathanlorfils.demo.repository.EnfantRepository;
import com.tp1_jonathanlorfils.demo.repository.PermisRepository;
import com.tp1_jonathanlorfils.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AppPermisTest {


    private SystemService systemService;

    @Autowired
    private PermisRepository permisRepository;

    @Autowired
    private CitoyenRepository citoyenRepository;

    @Autowired
    private EnfantRepository enfantRepository;

    @BeforeAll
    public void insertData(){
        Citoyen u1 = new Citoyen();
        Citoyen u2 = new Citoyen();
        Enfant u3 = new Enfant();

        PermisTest p1 = new PermisTest();
        p1.setTypePermis("Test");
        PermisVaccin p2 = new PermisVaccin();
        p2.setTypePermis("Vaccin");
        PermisVaccin p3 = new PermisVaccin();
        p3.setTypePermis("Vaccin");

        u1.setNumeroAssuranceSocial("123456789"); u1.setNom("Lorfils"); u1.setPrenom("Jonathan"); u1.setNumeroTelephone("5141231234");
        u2.setMotDePasse("mdp"); u2.setNumeroAssuranceSocial("987654321"); u2.setNom("Terrieur"); u2.setPrenom("Alain"); u2.setNumeroTelephone("5141231234"); u2.setCourriel("email@gmail.com");
        u3.setMotDePasse("mdpEnfant"); u3.setNumeroAssuranceSocial("1234"); u3.setPrenom("Alex"); u3.setNom("Terrieur"); u3.setTuteur(u1);

        citoyenRepository.saveAll(Arrays.asList(u1,u2));

        permisRepository.saveAll(Arrays.asList(p1,p2,p3));

    }

    @Test
    public void testfindCitoyenByNumeroAssuranceSocial(){
        assertNotNull(citoyenRepository.findCitoyenByNumeroAssuranceSocial("123456789"));
    }

    @Test
    public void findCitoyenByNomAndPrenomAndNumeroTelephone(){
        assertNotNull(citoyenRepository.findCitoyenByNomAndPrenomAndNumeroTelephone("Lorfils","Jonathan","5141231234"));
    }

    @Test
    public void findCitoyenByCourrielAndNumeroTelephone(){
        assertNotNull(citoyenRepository.findCitoyenByCourrielAndNumeroTelephone("email@gmail.com","5141231234"));
    }

    @Test
    public void findPermisByTypePermis(){
        assertEquals(2,permisRepository.findPermisByTypePermis("Vaccin").size());
    }

    @Test
    public void findPermisByIdPermis(){
        assertNotNull(permisRepository.findPermisByIdPermis(3));
    }

    @Test
    public void findEnfantByTuteur(){
        Citoyen citoyen1 = new Citoyen();
        citoyen1.setNumeroAssuranceSocial("1");
        Enfant enfant1 = new Enfant();
        enfant1.setTuteur(citoyen1);
        citoyenRepository.save(citoyen1);
        enfantRepository.save(enfant1);
        assertNotNull(enfantRepository.findEnfantByTuteur(citoyen1));
    }

    @Test
    public void test(){

        systemService.isLoginExist("Username");
    }
}
