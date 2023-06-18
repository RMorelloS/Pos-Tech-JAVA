package com.techchallenge.Monitoring_API.controller.form;

import com.techchallenge.Monitoring_API.domain.Pessoa;
import jakarta.validation.constraints.NotBlank;
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


    public Pessoa getPessoa(PessoaForm pessoaForm) {
        return new Pessoa(pessoaForm.nome,
                pessoaForm.dataNascimento,
                pessoaForm.sexo,
                pessoaForm.parentescoUsuario);
    }

    public Pessoa toPessoa(PessoaForm pessoaForm) {
        return new Pessoa(pessoaForm.getNome(),
                pessoaForm.getDataNascimento(),
                pessoaForm.getSexo(),
                pessoaForm.getParentescoUsuario());
    }
}
