package com.techchallenge.Monitoring_API.service;

import com.techchallenge.Monitoring_API.controller.form.UsuarioForm;
import com.techchallenge.Monitoring_API.domain.Eletrodomestico;
import com.techchallenge.Monitoring_API.domain.Endereco;
import com.techchallenge.Monitoring_API.domain.Pessoa;
import com.techchallenge.Monitoring_API.domain.Usuario;
import com.techchallenge.Monitoring_API.repositorio.RepositorioUsuario;
import com.techchallenge.Monitoring_API.service.exception.ControllerNotFoundException;
import com.techchallenge.Monitoring_API.service.exception.DatabaseException;
import com.techchallenge.Monitoring_API.service.exception.JpaObjectRetrievalFailureException;
import com.techchallenge.Monitoring_API.service.exception.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class UsuarioService {
    @Autowired
    private RepositorioUsuario repoUsuario;
    @Autowired
    private ValidatorService validatorService;
    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private EletrodomesticoService eletroService;
    public Collection<Usuario> findAll() {
        return repoUsuario.findAll();
    }

    public Usuario save(UsuarioForm usuarioForm) {
        Map<Path, String> violacoesToMap = validatorService.validarInput(usuarioForm);
        if(!violacoesToMap.isEmpty()){
            throw new ValidationException("Erro na validação dos campos: " + violacoesToMap);
        }
        var usuario = usuarioForm.toUsuario(usuarioForm);
        try{
            repoUsuario.save(usuario);
        }catch(org.springframework.orm.jpa.JpaObjectRetrievalFailureException e){
            throw new JpaObjectRetrievalFailureException("Erro de integridade" +
                    " no cadastro do usuário. Identificador do endereço não" +
                    " encontrado.");
        }
        return usuario;
    }
    public Usuario update(Usuario usuario) {
        try {
            Map<Path, String> violacoesToMap = validatorService.validarInput(usuario);
            if (!violacoesToMap.isEmpty()) {
                throw new ValidationException("Erro na validação dos campos: " + violacoesToMap);
            }

            Usuario usuarioBusca = repoUsuario.getOne(usuario.getIdUsuario());
            usuarioBusca.setLoginUsuario(usuario.getLoginUsuario());
            return usuarioBusca;
        }catch(EntityNotFoundException e){
            throw new ControllerNotFoundException("Usuário não encontrado, id:" + usuario.getIdUsuario());
        }catch(org.springframework.orm.jpa.JpaObjectRetrievalFailureException e){
            throw new JpaObjectRetrievalFailureException("Erro de integridade" +
                    " na atualização de cadastro do usuário. Identificador do usuário não" +
                    " encontrado.");
        }
    }

    public void delete(UUID id) {
        try {
            repoUsuario.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ControllerNotFoundException("Usuário não encontrado com id: " + id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException("Violação de integridade da base");
        }
    }

    public Usuario findById(UUID id) {
        var usuario = repoUsuario.findById(id).orElseThrow(() -> new ControllerNotFoundException("Usuário não encontrado"));
        return usuario;
    }

    public List<Pessoa> listarPessoas(UUID endereco) {
        List<Pessoa> listaPessoas = new ArrayList<Pessoa>();
        listaPessoas = pessoaService.findByEndereco(endereco);
        return listaPessoas;
    }

    public List<Eletrodomestico> listarEletros(UUID idEndereco) {
        List<Eletrodomestico> listaEletros = new ArrayList<Eletrodomestico>();
        listaEletros = eletroService.findByEndereco(idEndereco);
        return listaEletros;
    }
}
