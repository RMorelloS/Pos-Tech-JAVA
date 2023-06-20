package com.techchallenge.Monitoring_API.service;

import com.techchallenge.Monitoring_API.controller.form.EletrodomesticoForm;
import com.techchallenge.Monitoring_API.domain.Eletrodomestico;
import com.techchallenge.Monitoring_API.repositorio.RepositorioEletrodomestico;
import com.techchallenge.Monitoring_API.service.exception.ControllerNotFoundException;
import com.techchallenge.Monitoring_API.service.exception.DatabaseException;
import com.techchallenge.Monitoring_API.service.exception.ValidationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

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
        repoEletrodomestico.save(eletrodomestico);
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
            throw new ControllerNotFoundException("Eletrodomestico não encontrado com id: " + id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException("Violação de integridade da base");
        }
    }

    public Eletrodomestico findById(UUID id) {
        var eletro = repoEletrodomestico.findById(id).orElseThrow(() -> new ControllerNotFoundException("Eletrodomestico não encontrado"));
        return eletro;
    }
}
