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
        enderecoUsuario.setIdEndereco((Long) (enderecos.size() + 1L));
        enderecos.add(enderecoUsuario);
    }

    public List<EnderecoUsuario> encontrarEnderecosPorParametro(String param, String value) {
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

    public void update(EnderecoUsuario enderecoUsuario) {
        var enderecoBuscado =  buscaEndereco(enderecoUsuario).get();
        enderecoBuscado.setRua(enderecoUsuario.getRua());
        enderecoBuscado.setBairro(enderecoUsuario.getBairro());
        enderecoBuscado.setCidade(enderecoUsuario.getCidade());
        enderecoBuscado.setNumero(enderecoUsuario.getNumero());
        enderecoBuscado.setNumero(enderecoUsuario.getNumero());
    }

    public Optional<EnderecoUsuario> buscaEndereco(EnderecoUsuario enderecoUsuario) {
        var endereco = enderecos.stream().filter(p -> p.equals(enderecoUsuario)).findFirst();
        return endereco;
    }

    public void delete(EnderecoUsuario enderecoUsuario) {
        enderecos.removeIf(p -> p.equals(enderecoUsuario));
    }
}
