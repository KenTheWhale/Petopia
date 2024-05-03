package com.petopia.petopia.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vet")
public class VETController {

    @GetMapping("/getVetProfile")
    public String getVetProfile(){
        return "Vet Profile";
    }

}
