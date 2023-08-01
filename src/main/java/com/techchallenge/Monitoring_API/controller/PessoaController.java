package com.techchallenge.Monitoring_API.controller;

import com.googlecode.jmapper.JMapper;
import com.techchallenge.Monitoring_API.controller.form.PessoaForm;
import com.techchallenge.Monitoring_API.domain.Pessoa;
import com.techchallenge.Monitoring_API.repositorio.RepositorioPessoa;
import com.techchallenge.Monitoring_API.service.PessoaService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pessoa")
@ComponentScan({"com.techchallenge.Monitoring_API.config"})
@ComponentScan({"com.techchallenge.Monitoring_API.repositorio"})
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private Validator validator;
    public PessoaController(PessoaService pessoaService){
        this.pessoaService = pessoaService;
    }
    @GetMapping
    public ResponseEntity consultarPessoas(){
        var pessoas = pessoaService.findAll();
        if(pessoas == null || pessoas.isEmpty()){
            return ResponseEntity.ok("Sem pessoas cadastradas!!");
        }else{
            return ResponseEntity.ok(pessoas);
        }
    }
    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable UUID id){
        var pessoa = pessoaService.findById(id);
        return ResponseEntity.ok(pessoa);

    }
    @PostMapping
    public ResponseEntity criarPessoa(@RequestBody PessoaForm pessoaForm){

        pessoaService.save(pessoaForm);
        return ResponseEntity.ok("Pessoa adicionada com sucesso!");
    }



    @PutMapping
    public Pessoa update(@RequestBody Pessoa pessoa){
        return pessoaService.update(pessoa);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable UUID id){

        pessoaService.delete(id);
        return ResponseEntity.ok("Pessoa excluída com sucesso!");
    }

}
