package com.techchallenge.Monitoring_API.controller;

import com.googlecode.jmapper.JMapper;
import com.techchallenge.Monitoring_API.controller.form.PessoaForm;
import com.techchallenge.Monitoring_API.domain.Pessoa;
import com.techchallenge.Monitoring_API.repositorio.RepositorioPessoa;
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
    private RepositorioPessoa repoPessoa;
    @Autowired
    private Validator validator;
    public PessoaController(RepositorioPessoa repoPessoa){
        this.repoPessoa = repoPessoa;
    }
    @GetMapping
    public ResponseEntity consultarPessoas(){
        var pessoas = repoPessoa.findAll();
        if(pessoas == null || pessoas.isEmpty()){
            return ResponseEntity.ok("Sem endereços cadastrados!!");
        }else{
            return ResponseEntity.ok(pessoas);
        }
    }

    @PostMapping
    public ResponseEntity criarPessoa(@RequestBody PessoaForm pessoaForm){
        Map<Path, String> violacoesToMap = validarInput(pessoaForm);
        if(!violacoesToMap.isEmpty()){
            return ResponseEntity.badRequest().body(violacoesToMap);
        }
        var pessoa = pessoaForm.getPessoa(pessoaForm);

        repoPessoa.save(pessoa);
        return ResponseEntity.ok("Endereço adicionado com sucesso!");
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
    public ResponseEntity update(@RequestBody Pessoa pessoa){
        Map<Path, String> violacoesToMap = validarInput(pessoa);
        if(!violacoesToMap.isEmpty()){
            return ResponseEntity.badRequest().body(violacoesToMap);
        }
        Optional<Pessoa> pessoaBuscada = repoPessoa.buscaPessoa(pessoa.getIdPessoa());
        if(pessoaBuscada == null){
            return ResponseEntity.badRequest().body("Endereço não encontrado");
        }
        repoPessoa.update(pessoa);
        return ResponseEntity.ok("Endereço atualizado com sucesso!");
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable UUID id){

        var pessoaBuscada = repoPessoa.
                buscaPessoa(id);
        if (pessoaBuscada.isPresent()){
            repoPessoa.delete(id);
            return ResponseEntity.ok("Excluído com sucesso!");
        }else{
            return ResponseEntity.badRequest().body("Pessoa não encontrada!");
        }

    }

}
