package com.techchallenge.Monitoring_API.controller;

import com.googlecode.jmapper.JMapper;
import com.techchallenge.Monitoring_API.controller.form.EnderecoUsuarioForm;
import com.techchallenge.Monitoring_API.domain.EnderecoUsuario;
import com.techchallenge.Monitoring_API.repositorio.RepositorioEnderecoUsuario;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validator;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Map;

@RestController
@RequestMapping("/endereco")
@ComponentScan({"com.techchallenge.Monitoring_API.config"})
@ComponentScan({"com.techchallenge.Monitoring_API.repositorio"})
public class EnderecoUsuarioController {

    @Autowired
    private RepositorioEnderecoUsuario repoEndereco;
    private JMapper<EnderecoUsuario, EnderecoUsuarioForm> enderecoUsuarioMapper;
    @Autowired
    private Validator validator;
    public EnderecoUsuarioController(RepositorioEnderecoUsuario repoEndereco,
                                     JMapper<EnderecoUsuario, EnderecoUsuarioForm> enderecoUsuarioMapper){
        this.repoEndereco = repoEndereco;
        this.enderecoUsuarioMapper = enderecoUsuarioMapper;
    }
    @GetMapping
    public ResponseEntity consultarEnderecos(){
        var enderecos = repoEndereco.findAll();
        if(enderecos == null || enderecos.isEmpty()){
            return ResponseEntity.ok("Sem endereços cadastrados!");
        }else{
            return ResponseEntity.ok(enderecos);
        }
    }

    @PostMapping
    public ResponseEntity criarEndereco(@RequestBody EnderecoUsuarioForm enderecoUsuarioForm){
        Map<Path, String> violacoesToMap = validarInput(enderecoUsuarioForm);
        var enderecoUsuario = enderecoUsuarioMapper.getDestination(enderecoUsuarioForm);
        repoEndereco.save(enderecoUsuario);
        return ResponseEntity.ok("");
    }

    private <T> Map<Path, String> validarInput(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);
        Map<Path, String> violacoesToMap = violacoes.stream()
                .collect(Collectors.toMap(
                        violacao -> violacao.getPropertyPath(), violacao -> violacao.getMessage()
                ));
        return violacoesToMap;
    }


}
