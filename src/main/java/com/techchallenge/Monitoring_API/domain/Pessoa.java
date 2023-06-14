package com.techchallenge.Monitoring_API.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;

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
    private UUID idPessoa;

}
