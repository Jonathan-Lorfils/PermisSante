package com.tp1_jonathanlorfils.demo;

import com.tp1_jonathanlorfils.demo.Service.SystemService;
import com.tp1_jonathanlorfils.demo.model.Citoyen;
import com.tp1_jonathanlorfils.demo.model.Permis;
import com.tp1_jonathanlorfils.demo.model.PermisTest;
import com.tp1_jonathanlorfils.demo.model.User;
import com.tp1_jonathanlorfils.demo.repository.CitoyenRepository;
import com.tp1_jonathanlorfils.demo.repository.PermisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(1)
public class insert implements CommandLineRunner {

        @Autowired
        private CitoyenRepository citoyenRepository;


        @Autowired
        private PermisRepository permisRepository;

        @Autowired
        private SystemService systemService;

        private void insertData() {
            Citoyen c1 = new Citoyen();
            PermisTest p1 = new PermisTest();
//            p1.setIdPermis(1);
            p1.setTypePermis("Test");
            c1.setNumeroAssuranceSocial("123456789");
            c1.setNom("Lorfils");
            c1.setPrenom("Jonathan");
            c1.setSexe("M");
            c1.setAge(18);
            c1.setCourriel("jonathan@gmail.com");
            c1.setMotDePasse("pwd");
            c1.setNumeroTelephone("5141231234");
            c1.setVilleResidence("Montreal");
            c1.setMotDePasse("toto1234");
            c1.setPermis(p1);

            this.permisRepository.save(p1);

            this.citoyenRepository.save(c1);
    }

    private void cleanData(){
            this.permisRepository.deleteAll();
            this.citoyenRepository.deleteAll();
    }

    @Override
    public void run(String... args) throws Exception{
            cleanData();
        insertData();
    }
}
