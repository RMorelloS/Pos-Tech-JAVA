package com.techchallenge.Monitoring_API.service;

import com.techchallenge.Monitoring_API.controller.form.EletrodomesticoForm;
import com.techchallenge.Monitoring_API.domain.Eletrodomestico;
import com.techchallenge.Monitoring_API.repositorio.RepositorioEletrodomestico;
import com.techchallenge.Monitoring_API.service.exception.ControllerNotFoundException;
import com.techchallenge.Monitoring_API.service.exception.ValidationErrorException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
            throw new ValidationErrorException("Erro na validação dos campos: " + violacoesToMap);
        }
        var eletrodomestico = eletrodomesticoForm.toEletrodomestico(eletrodomesticoForm);
        repoEletrodomestico.save(eletrodomestico);
        return eletrodomestico;
    }
    public Eletrodomestico update(Eletrodomestico eletrodomestico) {
        try {
            Map<Path, String> violacoesToMap = validatorService.validarInput(eletrodomestico);
            if (!violacoesToMap.isEmpty()) {
                throw new ValidationErrorException("Erro na validação dos campos: " + violacoesToMap);
            }

            Eletrodomestico eletrodomesticoBusca = repoEletrodomestico.getOne(eletrodomestico.getIdEletrodomestico());
            eletrodomesticoBusca.setModelo(eletrodomestico.getModelo());
            eletrodomesticoBusca.setPotencia(eletrodomestico.getPotencia());
            eletrodomesticoBusca.setNome(eletrodomestico.getNome());
            eletrodomesticoBusca = repoEletrodomestico.save(eletrodomesticoBusca);
            return eletrodomesticoBusca;
        }catch(EntityNotFoundException e){
            throw new ControllerNotFoundException("Eletrodomestico não encontrado, id:" + eletrodomestico.getIdEletrodomestico());
        }
    }

    public void delete(UUID id) {
        try {
            repoEletrodomestico.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new EntityNotFoundException("Eletrodomestico não encontrado com id: " + id);
        }catch(DataIntegrityViolationException e){
            throw new EntityNotFoundException("Violação de integridade da base");
        }
    }
}
