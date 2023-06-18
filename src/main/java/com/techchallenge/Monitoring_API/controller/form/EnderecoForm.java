package com.techchallenge.Monitoring_API.controller.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techchallenge.Monitoring_API.domain.Endereco;
import lombok.Getter;
import lombok.Setter;

public class EnderecoForm {
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

    public Endereco toEndereco(EnderecoForm enderecoForm) {
        return new Endereco(enderecoForm.rua,
                enderecoForm.numero,
                enderecoForm.bairro,
                enderecoForm.cidade,
                enderecoForm.estado);
    }
}
