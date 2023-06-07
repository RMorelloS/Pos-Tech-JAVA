package com.techchallenge.Monitoring_API.repositorio;

import com.techchallenge.Monitoring_API.domain.EnderecoUsuario;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
public class RepositorioEnderecoUsuario {

    private Set<EnderecoUsuario> enderecos;
    public RepositorioEnderecoUsuario(){
        enderecos = new HashSet<>();
    }
    public Collection findAll() {
        return enderecos;
    }

    public void save(EnderecoUsuario enderecoUsuario) {
        enderecos.add(enderecoUsuario);
    }

}
