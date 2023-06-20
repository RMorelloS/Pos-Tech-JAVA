package com.techchallenge.Monitoring_API.controller.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techchallenge.Monitoring_API.domain.Eletrodomestico;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class EletrodomesticoForm {
    @JsonProperty
    @Getter
    @Setter
    @NotBlank(message = "Campo 'nome' é obrigatório e não pode estar vazio")
    private String nome;
    @JsonProperty
    @Getter
    @Setter
    @Min(value=0L, message = "Campo 'potencia' deve ser um inteiro positivo")
    private int potencia;
    @JsonProperty
    @Getter
    @Setter
    @NotBlank(message = "Campo 'modelo' é obrigatório e não pode estar vazio")
    private String modelo;

    public Eletrodomestico toEletrodomestico(EletrodomesticoForm eletrodomesticoForm) {
        return new Eletrodomestico(eletrodomesticoForm.nome,
                eletrodomesticoForm.potencia,
                eletrodomesticoForm.modelo);
    }
}
