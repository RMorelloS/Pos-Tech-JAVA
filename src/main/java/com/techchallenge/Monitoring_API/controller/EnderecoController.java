
//TODO fazer os relacionamentos
//TODO testar as requisições
//TODO fazer a documentação

package com.techchallenge.Monitoring_API.controller;

import com.techchallenge.Monitoring_API.controller.form.EnderecoForm;
import com.techchallenge.Monitoring_API.domain.Endereco;
import com.techchallenge.Monitoring_API.service.EnderecoService;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/endereco")
@ComponentScan({"com.techchallenge.Monitoring_API.config"})
@ComponentScan({"com.techchallenge.Monitoring_API.repositorio"})
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private Validator validator;
    public EnderecoController(EnderecoService enderecoService){
        this.enderecoService = enderecoService;
    }
    @GetMapping
    public ResponseEntity consultarEnderecos(){
        var enderecos = enderecoService.findAll();
        if(enderecos == null || enderecos.isEmpty()){
            return ResponseEntity.ok("Sem endereços cadastrados!!");
        }else{
            return ResponseEntity.ok(enderecos);
        }
    }

    @PostMapping
    public ResponseEntity criarEndereco(@RequestBody EnderecoForm enderecoForm){

        enderecoService.save(enderecoForm);
        return ResponseEntity.ok("Endereço adicionado com sucesso!");
    }


    @PutMapping
    public Endereco update(@RequestBody Endereco endereco){

        var enderecoBusca = enderecoService.update(endereco);
        return enderecoBusca;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id){
        enderecoService.delete(id);
        return ResponseEntity.ok("Endereço deletado com sucesso!");
    }

}
