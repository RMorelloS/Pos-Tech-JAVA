package com.techchallenge.Monitoring_API.controller.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techchallenge.Monitoring_API.domain.Eletrodomestico;
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

    public Eletrodomestico toEletrodomestico(EletrodomesticoForm eletrodomesticoForm) {
        return new Eletrodomestico(eletrodomesticoForm.nome,
                eletrodomesticoForm.potencia,
                eletrodomesticoForm.modelo);
    }
}
