package com.tp1_jonathanlorfils.demo.controller;

import com.tp1_jonathanlorfils.demo.Service.SystemService;
import com.tp1_jonathanlorfils.demo.model.Citoyen;
import com.tp1_jonathanlorfils.demo.model.PermisTest;
import com.tp1_jonathanlorfils.demo.model.PermisVaccin;
import com.tp1_jonathanlorfils.demo.repository.CitoyenRepository;
import com.tp1_jonathanlorfils.demo.repository.PermisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4220")
public class CitoyenController {

    @Autowired
    CitoyenRepository citoyenRepository;

    @Autowired
    PermisRepository permisRepository;

    @Autowired
    SystemService systemService;

    @GetMapping("/permisSante/{email}/{password}")
    public Citoyen login(@PathVariable("email") String email, @PathVariable("password") String password){
        return citoyenRepository.findCitoyenByCourrielAndMotDePasse(email, password);
    }

    @RequestMapping(value = "/permisSante", method = RequestMethod.POST)
    public Citoyen subscribe(@RequestBody Citoyen citoyen) throws Exception {
        String nom = citoyen.getNom();
        String prenom = citoyen.getPrenom();
        // creer code QR
        byte[] codeQr = systemService.generateQR(prenom + " " + nom +
                "possede un permis" + citoyen.getTypePermis(), "..\\Tp1_Jonathan-Lorfils\\codeQR\\codeQr.png");
        // creer pdf
        systemService.generatePDF("..\\Tp1_Jonathan-Lorfils\\codeQR",
                "../Tp1_Jonathan-Lorfils/codeQR","codeQr.png");
        // send email
        systemService.sendEmail(citoyen.getCourriel(),"Permis Sante de " + prenom + " " + nom,
                "corps","..\\Tp1_Jonathan-Lorfils\\codeQR", true);
        if (citoyen.getTypePermis().equals("Vaccin")){
            PermisVaccin permisVaccin = new PermisVaccin();
            permisVaccin.setTypePermis("Vaccin");
            permisVaccin.setCodeQR(codeQr);
            permisRepository.save(permisVaccin);
            citoyen.setPermis(permisVaccin);
        } else if (citoyen.getTypePermis().equals("Test")){
            PermisTest permisTest = new PermisTest();
            permisTest.setTypePermis("Test");
            permisTest.setCodeQR(codeQr);
            permisRepository.save(permisTest);
            citoyen.setPermis(permisTest);
        }
        return citoyenRepository.save(citoyen);
    }
}
