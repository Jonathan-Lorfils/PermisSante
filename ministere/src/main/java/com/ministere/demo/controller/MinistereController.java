package com.ministere.demo.controller;

import com.ministere.demo.service.MinistereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:4220")
public class MinistereController {

    @Autowired
    MinistereService service;

    @GetMapping("/ministere/{nassm}")
    @ResponseBody
    public boolean checkCitizenValidity(@PathVariable String nassm){
        return service.checkCitizenValidity(nassm);
    }
}
