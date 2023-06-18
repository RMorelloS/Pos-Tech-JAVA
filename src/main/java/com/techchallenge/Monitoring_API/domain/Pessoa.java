package com.techchallenge.Monitoring_API.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="tb_pessoa")

public class Pessoa {
    public Pessoa(String nome, LocalDate dataNascimento, String sexo, String parentescoUsuario) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.parentescoUsuario = parentescoUsuario;
        this.idPessoa = UUID.randomUUID();
    }

    public Pessoa() {
    }

    @Getter
    @Setter
    @NotBlank(message = "Campo 'nome' é obrigatório e não pode estar vazio")
    private String nome;
    @Getter
    @Setter
    @NotBlank(message = "Campo 'data de nascimento' é obrigatório e não pode estar vazio")
    private LocalDate dataNascimento;
    @Getter
    @Setter
    @NotBlank(message = "Campo 'sexo' é obrigatório e não pode estar vazio")
    private String sexo;
    @Getter
    @Setter
    @NotBlank(message = "Campo 'parentesco' é obrigatório e não pode estar vazio")
    private String parentescoUsuario;
    @Getter
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID idPessoa;


}
