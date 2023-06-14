package com.techchallenge.Monitoring_API.repositorio;

import com.techchallenge.Monitoring_API.domain.Eletrodomestico;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RepositorioEletrodomestico {

    private Set<Eletrodomestico> eletrodomesticos;
    public RepositorioEletrodomestico(){
        eletrodomesticos = new HashSet<>();
    }
    public Collection findAll() {
        return eletrodomesticos;
    }

    public void save(Eletrodomestico eletrodomestico) {
        eletrodomesticos.add(eletrodomestico);
    }

    public void update(Eletrodomestico eletrodomestico) {
        var eletrodomesticoBusca =  buscaEletrodomestico(eletrodomestico.getIdEletrodomestico()).get();
        eletrodomesticoBusca.setNome(eletrodomesticoBusca.getNome());
        eletrodomesticoBusca.setModelo(eletrodomesticoBusca.getModelo());
        eletrodomesticoBusca.setPotencia(eletrodomesticoBusca.getPotencia());
    }

    public Optional<Eletrodomestico> buscaEletrodomestico(UUID id) {
        var eletrodomestico = eletrodomesticos.stream().filter(p -> p.getIdEletrodomestico()==id).findFirst();
        return eletrodomestico;
    }

    public void delete(UUID id) {
        eletrodomesticos.removeIf(p -> p.getIdEletrodomestico() == id);
    }
}
