package com.techchallenge.Monitoring_API.controller;

import com.techchallenge.Monitoring_API.controller.form.UsuarioForm;
import com.techchallenge.Monitoring_API.domain.Eletrodomestico;
import com.techchallenge.Monitoring_API.domain.Usuario;
import com.techchallenge.Monitoring_API.service.EletrodomesticoService;
import com.techchallenge.Monitoring_API.service.EnderecoService;
import com.techchallenge.Monitoring_API.service.PessoaService;
import com.techchallenge.Monitoring_API.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuario")
@ComponentScan({"com.techchallenge.Monitoring_API.config"})
@ComponentScan({"com.techchallenge.Monitoring_API.repositorio"})

public class UsuarioController {

    @Autowired
    private EnderecoService enderecoService;
    @Autowired
    private EletrodomesticoService eletroService;

    @Autowired
    private PessoaService pessoaService;

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
    @RequestMapping(path="/buscarPorEndereco/{repositoryName}/{idEndereco}", method=RequestMethod.GET)
    public ResponseEntity buscarPorEndereco(@PathVariable String repositoryName,
                                            @PathVariable UUID idEndereco){
        if(repositoryName.equals("pessoas")) {
            return ResponseEntity.ok(usuarioService.listarPessoas(idEndereco));
        }else if(repositoryName.equals("eletro")){
            return ResponseEntity.ok(usuarioService.listarEletros(idEndereco));
        }
        return new ResponseEntity("Requisição inválida. Utilizar o formato:" +
                "/tipo/id, sendo:" +
                "Tipo: pessoas ou eletro." +
                "Id: id do objeto que se deseja buscar", HttpStatus.BAD_REQUEST);
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

    @RequestMapping(path="/{repositoryName}/{paramName}/{param}", method=RequestMethod.GET)
    public ResponseEntity findByParam(@PathVariable String repositoryName, @PathVariable String paramName, @PathVariable String param){
        if(repositoryName.equals("eletro")) {
            return ResponseEntity.ok(eletroService.findByParam(param, paramName));
        }else if(repositoryName.equals("endereco")){
            return ResponseEntity.ok(enderecoService.findByParam(param, paramName));
        }else if(repositoryName.equals("pessoas")){
            return ResponseEntity.ok(pessoaService.findByParam(param, paramName));
        }
        return new ResponseEntity("Requisição inválida. Utilizar o formato:" +
                "/tipo/atributo/texto, sendo:" +
                "Tipo: buscaEletros, buscaEnderecos ou buscaPessoas." +
                "Atributo: campo que se deseja buscar, como nome, rua, ..." +
                "Texto: valor do atributo que se deseja buscar, como João, Rua 1, ...", HttpStatus.BAD_REQUEST);
    }



}
