package com.techchallenge.Monitoring_API.repositorio;

import com.techchallenge.Monitoring_API.domain.Pessoa;
import com.techchallenge.Monitoring_API.domain.Pessoa;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RepositorioPessoa {

    private Set<Pessoa> pessoas;
    public RepositorioPessoa(){
        pessoas = new HashSet<>();
    }
    public Collection findAll() {
        return pessoas;
    }

    public void save(Pessoa Pessoa) {
        pessoas.add(Pessoa);
    }
    public void update(Pessoa pessoa) {
        var PessoaBuscado =  buscaPessoa(pessoa.getId()).get();
        PessoaBuscado.setNome(pessoa.getNome());
        PessoaBuscado.setDataNascimento(pessoa.getDataNascimento());
        PessoaBuscado.setSexo(pessoa.getSexo());
        PessoaBuscado.setParentescoUsuario(pessoa.getParentescoUsuario());
    }

    public Optional<Pessoa> buscaPessoa(UUID id) {
        var pessoaBuscada = pessoas.stream().filter(p -> p.getId() == id).findFirst();
        return pessoaBuscada;
    }

    public void delete(UUID id) {
        pessoas.removeIf(p -> p.getId() == id);
    }
}
