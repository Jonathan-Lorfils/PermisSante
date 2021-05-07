package com.ministere.demo.service;

import com.ministere.demo.model.Citoyen;
import com.ministere.demo.repository.CitoyenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MinistereService {

    @Autowired
    CitoyenRepository repository;

    public boolean checkCitizenValidity(String nassm){
        boolean flag = false;
        try{
            if (repository.findCitoyenByNassm(nassm) != null){
                flag = true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }
}
