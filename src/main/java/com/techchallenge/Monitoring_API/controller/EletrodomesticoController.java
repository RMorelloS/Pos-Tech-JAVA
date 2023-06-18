package com.techchallenge.Monitoring_API.controller;

import com.techchallenge.Monitoring_API.controller.form.EletrodomesticoForm;
import com.techchallenge.Monitoring_API.domain.Eletrodomestico;
import com.techchallenge.Monitoring_API.repositorio.RepositorioEletrodomestico;
import com.techchallenge.Monitoring_API.service.EletrodomesticoService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eletrodomestico")
@ComponentScan({"com.techchallenge.Monitoring_API.config"})
@ComponentScan({"com.techchallenge.Monitoring_API.repositorio"})
public class EletrodomesticoController {


    @Autowired private EletrodomesticoService eletroService;
    @GetMapping
    public ResponseEntity consultarEletrodomesticos(){
        var eletrodomesticos = eletroService.findAll();
        if(eletrodomesticos == null || eletrodomesticos.isEmpty()){
            return ResponseEntity.ok("Sem eletrodomésticos cadastrados!!");
        }else{
            return ResponseEntity.ok(eletrodomesticos);
        }
    }
    public EletrodomesticoController(EletrodomesticoService eletroService){
        this.eletroService = eletroService;
    }

    @PostMapping
    public ResponseEntity criarEletrodomestico(@RequestBody EletrodomesticoForm eletrodomesticoForm){

        eletroService.save(eletrodomesticoForm);
        return ResponseEntity.ok("Eletrodoméstico adicionado com sucesso!");
    }



    @PutMapping
    public Eletrodomestico update(@RequestBody Eletrodomestico eletrodomestico){
        return eletroService.update(eletrodomestico);

    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable UUID id){
        eletroService.delete(id);
        return ResponseEntity.ok("Eletrodoméstico deletado com sucesso!");

    }

}
