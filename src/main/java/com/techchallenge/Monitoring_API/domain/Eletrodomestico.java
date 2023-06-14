package com.techchallenge.Monitoring_API.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class Eletrodomestico {
    @Getter
    @Setter
    private String nome;
    @Getter
    @Setter
    private int potencia;
    @Getter
    @Setter
    private String modelo;

    public Eletrodomestico() {
    }

    public Eletrodomestico(String nome, int potencia, String modelo) {
        this.nome = nome;
        this.potencia = potencia;
        this.modelo = modelo;
        this.idEletrodomestico = UUID.randomUUID();
    }

    @Getter
    private UUID idEletrodomestico;
}
