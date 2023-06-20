package com.techchallenge.Monitoring_API.controller.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techchallenge.Monitoring_API.domain.Endereco;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class EnderecoForm {
    @JsonProperty
    @Getter
    @Setter
    @NotBlank(message = "Campo 'rua' é obrigatório e não pode estar vazio")
    private String rua;
    @JsonProperty
    @Getter
    @Setter
    @Min(value=0L, message = "Campo 'numero' deve ser um inteiro positivo")
    private int numero;
    @JsonProperty
    @Getter
    @Setter
    @NotBlank(message = "Campo 'bairro' é obrigatório e não pode estar vazio")
    private String bairro;
    @JsonProperty
    @Getter
    @Setter
    @NotBlank(message = "Campo 'cidade' é obrigatório e não pode estar vazio")
    private String cidade;
    @JsonProperty
    @Getter
    @Setter
    @NotBlank(message = "Campo 'estado' é obrigatório e não pode estar vazio")
    private String estado;

    public Endereco toEndereco(EnderecoForm enderecoForm) {
        return new Endereco(enderecoForm.rua,
                enderecoForm.numero,
                enderecoForm.bairro,
                enderecoForm.cidade,
                enderecoForm.estado);
    }
}
