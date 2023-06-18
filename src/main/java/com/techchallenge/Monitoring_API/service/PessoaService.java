package com.techchallenge.Monitoring_API.service;

import com.techchallenge.Monitoring_API.controller.form.PessoaForm;
import com.techchallenge.Monitoring_API.domain.Pessoa;
import com.techchallenge.Monitoring_API.repositorio.RepositorioPessoa;
import com.techchallenge.Monitoring_API.service.exception.ControllerNotFoundException;
import com.techchallenge.Monitoring_API.service.exception.ValidationErrorException;
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
public class PessoaService {
    @Autowired
    private RepositorioPessoa repoPessoa;
    @Autowired
    private ValidatorService validatorService;

    public Collection<Pessoa> findAll() {
        return repoPessoa.findAll();
    }

    public Pessoa save(PessoaForm pessoaForm) {
        Map<Path, String> violacoesToMap = validatorService.validarInput(pessoaForm);
        if(!violacoesToMap.isEmpty()){
            throw new ValidationErrorException("Erro na validação dos campos: " + violacoesToMap);
        }
        var pessoa = pessoaForm.toPessoa(pessoaForm);
        repoPessoa.save(pessoa);
        return pessoa;
    }
    public Pessoa update(Pessoa pessoa) {
        try {
            Map<Path, String> violacoesToMap = validatorService.validarInput(pessoa);
            if (!violacoesToMap.isEmpty()) {
                throw new ValidationErrorException("Erro na validação dos campos: " + violacoesToMap);
            }

            Pessoa pessoaBusca = repoPessoa.getOne(pessoa.getIdPessoa());
            pessoaBusca.setSexo(pessoa.getSexo());
            pessoaBusca.setNome(pessoa.getNome());
            pessoaBusca.setDataNascimento(pessoa.getDataNascimento());
            pessoaBusca.setParentescoUsuario(pessoa.getParentescoUsuario());
            pessoaBusca = repoPessoa.save(pessoaBusca);
            return pessoaBusca;
        }catch(EntityNotFoundException e){
            throw new ControllerNotFoundException("Endereço não encontrado, id:" + pessoa.getIdPessoa());
        }
    }

    public void delete(UUID id) {
        try{
            repoPessoa.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new EntityNotFoundException("Pessoa não encontrada com id: " + id);
        }catch(DataIntegrityViolationException e){
            throw new EntityNotFoundException("Violação de integridade da base");
        }
    }
}
