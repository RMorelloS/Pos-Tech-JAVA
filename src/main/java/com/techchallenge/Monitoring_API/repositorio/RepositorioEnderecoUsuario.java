package com.techchallenge.Monitoring_API.repositorio;

import com.techchallenge.Monitoring_API.domain.EnderecoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface RepositorioEnderecoUsuario extends JpaRepository<EnderecoUsuario, UUID> {

}
