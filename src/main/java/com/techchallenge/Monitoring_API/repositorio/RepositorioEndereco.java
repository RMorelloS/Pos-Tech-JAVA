package com.techchallenge.Monitoring_API.repositorio;

import com.techchallenge.Monitoring_API.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface RepositorioEndereco extends JpaRepository<Endereco, UUID> {
    List<Endereco> findByRua(String rua);
    List<Endereco> findByBairro(String bairro);
    List<Endereco> findByCidade(String cidade);
    List<Endereco> findByEstado(String estado);

}
