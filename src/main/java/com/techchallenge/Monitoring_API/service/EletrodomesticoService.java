package com.techchallenge.Monitoring_API.service;

import com.techchallenge.Monitoring_API.controller.form.EletrodomesticoForm;
import com.techchallenge.Monitoring_API.domain.Eletrodomestico;
import com.techchallenge.Monitoring_API.repositorio.RepositorioEletrodomestico;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private Validator validator;

    public Collection<Eletrodomestico> findAll() {
        return repoEletrodomestico.findAll();
    }

    public Eletrodomestico save(EletrodomesticoForm eletrodomesticoForm) {
        Map<Path, String> violacoesToMap = validarInput(eletrodomesticoForm);
        if(!violacoesToMap.isEmpty()){
         //throw new...   return ResponseEntity.badRequest().body(violacoesToMap);
        }
        var eletrodomesticoBusca = eletrodomesticoForm.toEletrodomestico(eletrodomesticoForm);
        return eletrodomesticoBusca;
    }
    private <T> Map<Path, String> validarInput(T form) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(form);
        Map<Path, String> violacoesToMap = violacoes.stream()
                .collect(Collectors.toMap(
                        violacao -> violacao.getPropertyPath(), violacao -> violacao.getMessage()
                ));
        return violacoesToMap;
    }

    public Eletrodomestico update(Eletrodomestico eletrodomestico) {

        Map<Path, String> violacoesToMap = validarInput(eletrodomestico);
        if(!violacoesToMap.isEmpty()){
            //throw new ...(violacoesToMap);
        }

        Eletrodomestico eletrodomesticoBusca = repoEletrodomestico.getOne(eletrodomestico.getIdEndereco());
        eletrodomesticoBusca.setModelo(eletrodomestico.getModelo());
        eletrodomesticoBusca.setPotencia(eletrodomestico.getPotencia());
        eletrodomesticoBusca.setNome(eletrodomestico.getNome());
        eletrodomesticoBusca = repoEletrodomestico.save(eletrodomesticoBusca);
        return eletrodomesticoBusca;
    }

    public void delete(UUID id) {
        repoEletrodomestico.deleteById(id);
    }
}
