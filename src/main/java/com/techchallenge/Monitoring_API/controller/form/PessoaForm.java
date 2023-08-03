package com.techchallenge.Monitoring_API.controller.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techchallenge.Monitoring_API.domain.Endereco;
import com.techchallenge.Monitoring_API.domain.Pessoa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class PessoaForm {
    public PessoaForm(String nome, LocalDate dataNascimento, String sexo, String parentescoUsuario) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.parentescoUsuario = parentescoUsuario;
    }

    public PessoaForm() {
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

    @JsonProperty
    @Getter
    @Setter
    private Endereco endereco;

    public Pessoa getPessoa(PessoaForm pessoaForm) {
        return new Pessoa(pessoaForm.nome,
                pessoaForm.dataNascimento,
                pessoaForm.sexo,
                pessoaForm.parentescoUsuario,
                pessoaForm.getEndereco());
    }

    public Pessoa toPessoa(PessoaForm pessoaForm) {
        return new Pessoa(pessoaForm.getNome(),
                pessoaForm.getDataNascimento(),
                pessoaForm.getSexo(),
                pessoaForm.getParentescoUsuario(),
                pessoaForm.getEndereco());
    }
}
