package com.techchallenge.Monitoring_API.repositorio;

import com.techchallenge.Monitoring_API.domain.Eletrodomestico;
import com.techchallenge.Monitoring_API.domain.Pessoa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

@Repository
public interface RepositorioEletrodomestico extends JpaRepository<Eletrodomestico, UUID> {

    public List<Eletrodomestico> findByNome(String nome);
    public List<Eletrodomestico> findByPotencia(Integer potencia);
    public List<Eletrodomestico> findByModelo(String modelo);

    @Query(nativeQuery = true, value="SELECT * FROM tb_eletrodomestico WHERE id_endereco=:idEndereco")
    List<Eletrodomestico> findByEndereco(@Param("idEndereco") UUID idEndereco);

}
