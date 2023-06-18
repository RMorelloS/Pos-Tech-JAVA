
//TODO fazer o tratamento de exceções
//TODO fazer os relacionamentos
//TODO testar as requisições
//TODO criar o service
//TODO fazer a documentação

package com.techchallenge.Monitoring_API.controller;

import com.techchallenge.Monitoring_API.controller.form.EnderecoUsuarioForm;
import com.techchallenge.Monitoring_API.domain.EnderecoUsuario;
import com.techchallenge.Monitoring_API.repositorio.RepositorioEnderecoUsuario;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/endereco")
@ComponentScan({"com.techchallenge.Monitoring_API.config"})
@ComponentScan({"com.techchallenge.Monitoring_API.repositorio"})
public class EnderecoUsuarioController {

    @Autowired
    private RepositorioEnderecoUsuario repoEndereco;
    @Autowired
    private Validator validator;
    public EnderecoUsuarioController(RepositorioEnderecoUsuario repoEndereco){
        this.repoEndereco = repoEndereco;
    }
    @GetMapping
    public ResponseEntity consultarEnderecos(){
        var enderecos = repoEndereco.findAll();
        if(enderecos == null || enderecos.isEmpty()){
            return ResponseEntity.ok("Sem endereços cadastrados!!");
        }else{
            return ResponseEntity.ok(enderecos);
        }
    }

    @PostMapping
    public ResponseEntity criarEndereco(@RequestBody EnderecoUsuarioForm enderecoUsuarioForm){
        Map<Path, String> violacoesToMap = validarInput(enderecoUsuarioForm);
        if(!violacoesToMap.isEmpty()){
            return ResponseEntity.badRequest().body(violacoesToMap);
        }
        var enderecoUsuario = enderecoUsuarioForm.toEndereco(enderecoUsuarioForm);

        repoEndereco.save(enderecoUsuario);
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
    public EnderecoUsuario update(@RequestBody EnderecoUsuario enderecoUsuario){
            Map<Path, String> violacoesToMap = validarInput(enderecoUsuario);
        if(!violacoesToMap.isEmpty()){
         //throw new ...   return ResponseEntity.badRequest().body(violacoesToMap);
        }
        EnderecoUsuario enderecoBusca = repoEndereco.getOne(enderecoUsuario.getIdEndereco());
        enderecoBusca.setBairro(enderecoUsuario.getBairro());
        enderecoBusca.setRua(enderecoUsuario.getRua());
        enderecoBusca.setNumero(enderecoUsuario.getNumero());
        enderecoBusca.setEstado(enderecoBusca.getEstado());
        enderecoBusca.setCidade(enderecoBusca.getCidade());
        enderecoBusca = repoEndereco.save(enderecoBusca);
        return enderecoBusca;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable UUID id){
        repoEndereco.deleteById(id);
    }

}
