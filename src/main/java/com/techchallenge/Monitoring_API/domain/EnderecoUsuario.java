package com.techchallenge.Monitoring_API.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class EnderecoUsuario {
    @Getter
    @Setter
    @NotBlank(message = "Campo 'rua' é obrigatório e não pode estar vazio")
    private String rua;
    @Getter
    @Setter
    @NotBlank(message = "Campo 'numero' é obrigatório e não pode estar vazio")
    private int numero;
    @Getter
    @Setter
    @NotBlank(message = "Campo 'bairro' é obrigatório e não pode estar vazio")
    private String bairro;
    @Getter
    @Setter
    @NotBlank(message = "Campo 'cidade' é obrigatório e não pode estar vazio")
    private String cidade;
    @Getter
    @Setter
    @NotBlank(message = "Campo 'estado' é obrigatório e não pode estar vazio")
    private String estado;
    @Getter
    @Setter
    private int IdEndereco;

}
