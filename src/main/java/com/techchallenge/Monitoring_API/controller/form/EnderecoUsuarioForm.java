package com.techchallenge.Monitoring_API.controller.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class EnderecoUsuarioForm {
    @JsonProperty
    @Getter
    @Setter
    private String rua;
    @JsonProperty
    @Getter
    @Setter
    private int numero;
    @JsonProperty
    @Getter
    @Setter
    private String bairro;
    @JsonProperty
    @Getter
    @Setter
    private String cidade;
    @JsonProperty
    @Getter
    @Setter
    private String estado;
    @Getter
    @Setter
    private int IdEndereco;
}
