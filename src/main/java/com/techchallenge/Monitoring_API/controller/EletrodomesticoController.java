package com.techchallenge.Monitoring_API.controller;

import com.googlecode.jmapper.JMapper;
import com.techchallenge.Monitoring_API.controller.form.EletrodomesticoForm;
import com.techchallenge.Monitoring_API.domain.Eletrodomestico;
import com.techchallenge.Monitoring_API.repositorio.RepositorioEletrodomestico;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/eletrodomestico")
@ComponentScan({"com.techchallenge.Monitoring_API.config"})
@ComponentScan({"com.techchallenge.Monitoring_API.repositorio"})
public class EletrodomesticoController {

    @Autowired
    private RepositorioEletrodomestico repoEletrodomestico;
    @Autowired
    private Validator validator;
    public EletrodomesticoController(RepositorioEletrodomestico repoEletrodomestico){
        this.repoEletrodomestico = repoEletrodomestico;
    }
    @GetMapping
    public ResponseEntity consultarEletrodomesticos(){
        var eletrodomesticos = repoEletrodomestico.findAll();
        if(eletrodomesticos == null || eletrodomesticos.isEmpty()){
            return ResponseEntity.ok("Sem eletrodomésticos cadastrados!!");
        }else{
            return ResponseEntity.ok(eletrodomesticos);
        }
    }

    @PostMapping
    public ResponseEntity criarEletrodomestico(@RequestBody EletrodomesticoForm eletrodomesticoForm){
        Map<Path, String> violacoesToMap = validarInput(eletrodomesticoForm);
        if(!violacoesToMap.isEmpty()){
            return ResponseEntity.badRequest().body(violacoesToMap);
        }
        var eletrodomesticoBusca = eletrodomesticoForm.toEletrodomestico(eletrodomesticoForm);

        repoEletrodomestico.save(eletrodomesticoBusca);
        return ResponseEntity.ok("Eletrodoméstico adicionado com sucesso!");
    }

    private <T> Map<Path, String> validarInput(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);
        Map<Path, String> violacoesToMap = violacoes.stream()
                .collect(Collectors.toMap(
                        violacao -> violacao.getPropertyPath(), violacao -> violacao.getMessage()
                ));
        return violacoesToMap;
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Eletrodomestico eletrodomestico){
        Map<Path, String> violacoesToMap = validarInput(eletrodomestico);
        if(!violacoesToMap.isEmpty()){
            return ResponseEntity.badRequest().body(violacoesToMap);
        }
        Optional<Eletrodomestico> eletrodomesticoBusca = repoEletrodomestico.findById(eletrodomestico.getIdEletrodomestico());
        if(eletrodomesticoBusca == null){
            return ResponseEntity.badRequest().body("Eletrodoméstico não encontrado");
        }
//        repoEletrodomestico.update(eletrodomestico);
        return ResponseEntity.ok("Eletrodoméstico atualizado com sucesso!");
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable UUID id){

        var eletrodomesticoBusca = repoEletrodomestico.
                findById(id);
        if (eletrodomesticoBusca.isPresent()){
           // repoEletrodomestico.delete(id);
            return ResponseEntity.ok("Excluído com sucesso!");
        }else{
            return ResponseEntity.badRequest().body("Endereço não encontrado!");
        }

    }

}
