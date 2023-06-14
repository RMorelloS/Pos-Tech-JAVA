package com.techchallenge.Monitoring_API.controller.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class EletrodomesticoForm {
    @JsonProperty
    @Getter
    @Setter
    private String nome;
    @JsonProperty
    @Getter
    @Setter
    private int potencia;
    @JsonProperty
    @Getter
    @Setter
    private String modelo;

}
