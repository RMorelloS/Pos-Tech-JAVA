package com.techchallenge.Monitoring_API.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="tb_eletrodomestico")
public class Eletrodomestico    {
    @Getter
    @Setter
    private String nome;
    @Getter
    @Setter
    private int potencia;
    @Getter
    @Setter
    private String modelo;

    @Getter
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID idEndereco;

    public Eletrodomestico() {
    }

    public Eletrodomestico(String nome, int potencia, String modelo) {
        this.nome = nome;
        this.potencia = potencia;
        this.modelo = modelo;
        this.idEndereco = UUID.randomUUID();
    }



}
