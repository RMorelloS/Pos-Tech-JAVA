package com.techchallenge.Monitoring_API.service;

import com.techchallenge.Monitoring_API.controller.form.EletrodomesticoForm;
import com.techchallenge.Monitoring_API.domain.Eletrodomestico;
import com.techchallenge.Monitoring_API.domain.Endereco;
import com.techchallenge.Monitoring_API.domain.Pessoa;
import com.techchallenge.Monitoring_API.repositorio.RepositorioEletrodomestico;
import com.techchallenge.Monitoring_API.service.exception.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class EletrodomesticoService {
    @Autowired
    private RepositorioEletrodomestico repoEletrodomestico;
    @Autowired
    private ValidatorService validatorService;

    public Collection<Eletrodomestico> findAll() {
        return repoEletrodomestico.findAll();
    }

    public Eletrodomestico save(EletrodomesticoForm eletrodomesticoForm) {
        Map<Path, String> violacoesToMap = validatorService.validarInput(eletrodomesticoForm);
        if(!violacoesToMap.isEmpty()){
            throw new ValidationException("Erro na validação dos campos: " + violacoesToMap);
        }
        var eletrodomestico = eletrodomesticoForm.toEletrodomestico(eletrodomesticoForm);
        try{
            repoEletrodomestico.save(eletrodomestico);
        }catch(org.springframework.orm.jpa.JpaObjectRetrievalFailureException e){
            throw new com.techchallenge.Monitoring_API.service
                    .exception
                    .JpaObjectRetrievalFailureException("Erro de integridade" +
                    " no cadastro do eletrodoméstico. Identificador do endereço não" +
                    " encontrado.");
        }
        return eletrodomestico;
    }
    public Eletrodomestico update(Eletrodomestico eletrodomestico) {
        try {
            Map<Path, String> violacoesToMap = validatorService.validarInput(eletrodomestico);
            if (!violacoesToMap.isEmpty()) {
                throw new ValidationException("Erro na validação dos campos: " + violacoesToMap);
            }

            Eletrodomestico eletrodomesticoBusca = repoEletrodomestico.getOne(eletrodomestico.getIdEletrodomestico());
            eletrodomesticoBusca.setModelo(eletrodomestico.getModelo());
            eletrodomesticoBusca.setPotencia(eletrodomestico.getPotencia());
            eletrodomesticoBusca.setNome(eletrodomestico.getNome());
            eletrodomesticoBusca.setEndereco(eletrodomestico.getEndereco());
            eletrodomesticoBusca = repoEletrodomestico.save(eletrodomesticoBusca);
            return eletrodomesticoBusca;
        }catch(EntityNotFoundException e){
            throw new ControllerNotFoundException("Eletrodomestico não encontrado, id:" + eletrodomestico.getIdEletrodomestico());
        }catch(org.springframework.orm.jpa.JpaObjectRetrievalFailureException e){
            throw new com.techchallenge.Monitoring_API.service
                    .exception
                    .JpaObjectRetrievalFailureException("Erro de integridade" +
                    " na atualização de cadastro do eletrodoméstico. Identificador do endereço não" +
                    " encontrado.");
        }
    }

    public void delete(UUID id) {
        try {
            repoEletrodomestico.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new ControllerNotFoundException("Eletrodomestico não encontrado com id: " + id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException("Violação de integridade da base");
        }
    }

    public Eletrodomestico findById(UUID id) {
        var eletro = repoEletrodomestico.findById(id).orElseThrow(() -> new ControllerNotFoundException("Eletrodomestico não encontrado"));
        return eletro;
    }
    public List<Eletrodomestico> findByEndereco(UUID endereco) {
        List<Eletrodomestico> listaEletros = repoEletrodomestico.findByEndereco(endereco);
        return listaEletros;
    }

    public List<Eletrodomestico> findByParam(String param, String paramName) {
        List<Eletrodomestico> listaEletrodomesticos = new ArrayList<>();
        switch(paramName) {
            case "nome":
                listaEletrodomesticos = repoEletrodomestico.findByNome(param);
                break;
            case "modelo":
                listaEletrodomesticos = repoEletrodomestico.findByModelo(param);
                break;
            case "potencia":
                listaEletrodomesticos = repoEletrodomestico.findByPotencia(Integer.valueOf(param));
                break;
        }
        return listaEletrodomesticos;
    }

    public Eletrodomestico alternarEstado(UUID id) {
        var eletro = new Eletrodomestico();
        try{
            eletro = repoEletrodomestico.findById(id).orElseThrow(() -> new ControllerNotFoundException("Eletrodomestico não encontrado"));

            if(!eletro.getEletro_ligado()){
                eletro.setEletro_ligado(true);
                eletro.setInicio_uso(LocalDateTime.now());
                eletro.setFim_uso(null);
            }else if(eletro.getEletro_ligado()){
                eletro.setEletro_ligado(false);
                eletro.setFim_uso(LocalDateTime.now());
                eletro.setTempo_uso(eletro.getTempo_uso() + ChronoUnit.SECONDS.between(eletro.getInicio_uso(), eletro.getFim_uso()));
            }
            eletro = repoEletrodomestico.save(eletro);
            return eletro;
        }catch(EntityNotFoundException e){
            throw new ControllerNotFoundException("Eletrodomestico não encontrado, id:" + eletro.getIdEletrodomestico());
        }

    }
}
