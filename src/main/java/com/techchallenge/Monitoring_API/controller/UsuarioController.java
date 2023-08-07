package com.techchallenge.Monitoring_API.controller;

import com.techchallenge.Monitoring_API.controller.form.UsuarioForm;
import com.techchallenge.Monitoring_API.domain.Usuario;
import com.techchallenge.Monitoring_API.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuario")
@ComponentScan({"com.techchallenge.Monitoring_API.config"})
@ComponentScan({"com.techchallenge.Monitoring_API.repositorio"})

public class UsuarioController {


    @Autowired private UsuarioService usuarioService;
    @GetMapping
    public ResponseEntity consultarUsuarios(){
        var usuarios = usuarioService.findAll();
        if(usuarios == null || usuarios.isEmpty()){
            return ResponseEntity.ok("Sem usuários cadastrados!!");
        }else{
            return ResponseEntity.ok(usuarios);
        }
    }
    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable UUID id){
        var usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);

    }
    @RequestMapping(path="/buscarPessoas/{idEndereco}", method=RequestMethod.GET)
    public ResponseEntity consultarUsuarios(@PathVariable UUID idEndereco){
        var enderecos = usuarioService.listarPessoas(idEndereco);
        if(enderecos == null || enderecos.isEmpty()){
            return ResponseEntity.ok("Sem endereço cadastrado para o usuário!!");
        }else{
            return ResponseEntity.ok(enderecos);
        }
    }

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity criarUsuario(@RequestBody UsuarioForm usuarioForm){

        usuarioService.save(usuarioForm);
        return ResponseEntity.ok("Usuário adicionado com sucesso!");
    }



    @PutMapping
    public Usuario update(@RequestBody Usuario usuario){
        return usuarioService.update(usuario);

    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable UUID id){
        usuarioService.delete(id);
        return ResponseEntity.ok("Usuário deletado com sucesso!");

    }

}
