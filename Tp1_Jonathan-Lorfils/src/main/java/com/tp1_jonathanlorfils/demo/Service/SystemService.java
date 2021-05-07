package com.tp1_jonathanlorfils.demo.Service;

import com.tp1_jonathanlorfils.demo.model.*;
import com.tp1_jonathanlorfils.demo.repository.CitoyenRepository;
import com.tp1_jonathanlorfils.demo.repository.PermisRepository;
import com.tp1_jonathanlorfils.demo.repository.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.qrcode.QRCodeWriter;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

@Service
public class SystemService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CitoyenRepository citoyenRepository;

    @Autowired
    private PermisRepository permisRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment environment;


    public String getCitizenValidity(String input){
        final String url = "http://localhost:9393/ministere/";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity(url + input, Boolean.class);

        return responseEntity.getBody().toString();
    }

    public void genererPermis(Citoyen citoyen, String typePermis){
        Permis permis;
        if (typePermis.equals("Vaccin")) {
            permis = new PermisVaccin();
        } else {
            permis = new PermisTest();
        }
        citoyen.setPermis(permis);
        citoyenRepository.save(citoyen);
        permisRepository.save(permis);
    }

    public void renouvellerPermis(Citoyen citoyen){
        genererPermis(citoyen,"Test");
    }

    public void motDePasseOublie(String courriel) throws Exception {
        if(isLoginExist(courriel)){
            Citoyen citoyen = citoyenRepository.findCitoyenByCourriel(courriel);
            citoyen.setMotDePasse(genererMotDePasseTemporaire());
            String titre = "Mot de passe Oublie Permis COVID";
            String texte = "Voici votre mot de passe temporaire :" + citoyen.getMotDePasse();
            sendEmail(citoyen.getCourriel(),titre,texte,"", false);
        }
    }

    public String genererMotDePasseTemporaire(){
        String motDePasseTemp;
        int min = Integer.parseInt(environment.getProperty("mdp.min"));
        int max = Integer.parseInt(environment.getProperty("mdp.max"));
        int numeroTemporaire = (int)Math.random() * (max - min + 1) + min;
        return motDePasseTemp = "COVIDPERMIS" + numeroTemporaire;
    }


    public boolean login(String courriel, String mdp){
        return citoyenRepository.findCitoyenByCourrielAndMotDePasse(courriel, mdp) != null;
    }

    public boolean isLoginExist(String courriel){
        return citoyenRepository.findCitoyenByCourriel(courriel) != null;
    }

    public List<Permis> AllPermis(){
        return permisRepository.findAll();
    }

    public byte[] generateQR(String data, String filePath) throws Exception {
//        Path path = FileSystems.getDefault().getPath(filePath);
//        QRCodeWriter qr = new QRCodeWriter();
//        MatrixToImageWriter.writeToPath(qr.encode(data, BarcodeFormat.QR_CODE,
//                Integer.parseInt(environment.getProperty("qrCode.width")),
//                Integer.parseInt(environment.getProperty("qrCode.height"))),
//                environment.getProperty("qrCode.extension"), path);
        FileInputStream inputStream = new FileInputStream(filePath);
        return IOUtils.toByteArray(inputStream);
    }

    public void generatePDF(String filePath, String pathServer, String imageName) throws Exception {
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);

        Document document = new Document(pdf);
        Image image = new Image(ImageDataFactory.create(pathServer + "/" + imageName));

        Paragraph p = new Paragraph("Bonjour Toi\n").add("Voici ton code permis santé").add(image);
        document.add(p);
        document.close();
    }

    public boolean sendEmail(String mailTo, String subject, String body, String filePath, boolean usageAttachement) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(mailTo);
        helper.setSubject(subject);
        helper.setText(body);
        if(usageAttachement){
            helper.addAttachment("codeQR.png", new File(filePath));
            helper.addAttachment("pdfQR.pdf", new File(filePath));
        }

        mailSender.send(message);
        return true;
    }
}
