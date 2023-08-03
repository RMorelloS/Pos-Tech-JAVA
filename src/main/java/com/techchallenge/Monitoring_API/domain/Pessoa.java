package com.techchallenge.Monitoring_API.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="tb_pessoa")

public class Pessoa {
    public Pessoa(String nome, LocalDate dataNascimento,
                  String sexo, String parentescoUsuario,
                  Endereco endereco) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.parentescoUsuario = parentescoUsuario;
        this.idPessoa = UUID.randomUUID();
        this.endereco = endereco;
    }

    public Pessoa() {
    }

    @Getter
    @Setter
    @NotBlank(message = "Campo 'nome' é obrigatório e não pode estar vazio")
    private String nome;
    @Getter
    @Setter
    @NotNull(message="Campo 'data de nascimento' é obrigatório e não pode estar vazio")
    @Past(message="Data de nascimento não pode ser superior ao dia de hoje")
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

    @Getter
    @Setter
    @JsonProperty
    @ManyToOne
    @JoinColumn(name="idEndereco", referencedColumnName = "idEndereco")
    private Endereco endereco;

}
