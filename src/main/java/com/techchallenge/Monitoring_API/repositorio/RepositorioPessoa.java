package com.techchallenge.Monitoring_API.repositorio;

import com.techchallenge.Monitoring_API.domain.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface RepositorioPessoa extends JpaRepository<Pessoa, UUID> {

    @Query(nativeQuery = true, value="SELECT * FROM tb_pessoa WHERE id_endereco=:idEndereco")
    List<Pessoa> findByEndereco(@Param("idEndereco") UUID idEndereco);

    List<Pessoa> findByNome(String nome);
    List<Pessoa> findByDataNascimento(LocalDate dataNascimento);
    List<Pessoa> findBySexo(String sexo);
    List<Pessoa> findByParentescoUsuario(String parentescoUsuario);

}
