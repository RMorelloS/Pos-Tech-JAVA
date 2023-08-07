package com.techchallenge.Monitoring_API.repositorio;
import com.techchallenge.Monitoring_API.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RepositorioUsuario extends JpaRepository<Usuario, UUID> {

}
