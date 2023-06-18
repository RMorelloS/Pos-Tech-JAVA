package com.techchallenge.Monitoring_API.service;

import com.techchallenge.Monitoring_API.controller.form.EnderecoForm;
import com.techchallenge.Monitoring_API.domain.Endereco;
import com.techchallenge.Monitoring_API.repositorio.RepositorioEndereco;
import com.techchallenge.Monitoring_API.repositorio.RepositorioEndereco;
import com.techchallenge.Monitoring_API.service.exception.ControllerNotFoundException;
import com.techchallenge.Monitoring_API.service.exception.ValidationErrorException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnderecoService {
    @Autowired
    private RepositorioEndereco repoEndereco;
    @Autowired
    private ValidatorService validatorService;

    public Collection<Endereco> findAll() {
        return repoEndereco.findAll();
    }

    public Endereco save(EnderecoForm EnderecoForm) {
        Map<Path, String> violacoesToMap = validatorService.validarInput(EnderecoForm);
        if(!violacoesToMap.isEmpty()){
            throw new ValidationErrorException("Erro na validação dos campos: " + violacoesToMap);
        }
        var endereco = EnderecoForm.toEndereco(EnderecoForm);
        repoEndereco.save(endereco);
        return endereco;
    }


    public Endereco update(Endereco endereco) {
        try{
            Map<Path, String> violacoesToMap = validatorService.validarInput(endereco);
            if(!violacoesToMap.isEmpty()){
                throw new ValidationErrorException("Erro na validação dos campos: " + violacoesToMap);
            }

            Endereco enderecoBusca = repoEndereco.getOne(endereco.getIdEndereco());
            enderecoBusca.setBairro(endereco.getBairro());
            enderecoBusca.setRua(endereco.getRua());
            enderecoBusca.setNumero(endereco.getNumero());
            enderecoBusca.setEstado(enderecoBusca.getEstado());
            enderecoBusca.setCidade(enderecoBusca.getCidade());
            enderecoBusca = repoEndereco.save(enderecoBusca);
            return enderecoBusca;
        }catch(EntityNotFoundException e){
            throw new ControllerNotFoundException("Endereço não encontrado, id:" + endereco.getIdEndereco());
        }

    }

    public void delete(UUID id) {
        try {
            repoEndereco.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new EntityNotFoundException("Endereco não encontrado com id: " + id);
        }catch(DataIntegrityViolationException e){
            throw new EntityNotFoundException("Violação de integridade da base");
        }
    }
}
