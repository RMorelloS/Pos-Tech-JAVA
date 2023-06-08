package com.techchallenge.Monitoring_API.repositorio;

import com.techchallenge.Monitoring_API.domain.EnderecoUsuario;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    public List<EnderecoUsuario> findByParam(String param, String value) {
        List enderecosRetorno = new ArrayList();
        switch((param.toString().toLowerCase())){
            case "rua":
                enderecosRetorno = List.of(enderecos.stream().filter(p ->
                        p.getRua().equals(value)).toArray());
                break;
            case "numero":
                enderecosRetorno = List.of(enderecos.stream().filter(p ->
                        p.getNumero() == Integer.getInteger(value)).toArray());
                break;
            case "bairro":
                enderecosRetorno = List.of(enderecos.stream().filter(p ->
                        p.getBairro().equals(value)).toArray());
                break;
            case "cidade":
                enderecosRetorno = List.of(enderecos.stream().filter(p ->
                        p.getCidade().equals(value)).toArray());
                break;
            case "estado":
                enderecosRetorno = List.of(enderecos.stream().filter(p ->
                        p.getEstado().equals(value)).toArray());
                break;
        }
        return enderecosRetorno;
    }

}
