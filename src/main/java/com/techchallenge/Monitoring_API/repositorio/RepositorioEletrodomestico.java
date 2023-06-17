package com.techchallenge.Monitoring_API.repositorio;

import com.techchallenge.Monitoring_API.domain.Eletrodomestico;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

@Repository
public interface RepositorioEletrodomestico extends JpaRepository<Eletrodomestico, UUID> {

}
