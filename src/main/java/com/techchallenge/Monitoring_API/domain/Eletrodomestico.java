package com.techchallenge.Monitoring_API.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="tb_eletrodomestico")
public class Eletrodomestico {

    @Getter
    @Setter
    @Column(columnDefinition="TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime inicio_uso;

    @Getter
    @Setter
    private Boolean eletro_ligado;


    @Getter
    @Setter
    @Column(columnDefinition="TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime fim_uso;

    @Getter
    @Setter
    private Double tempo_uso;

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
    private UUID idEletrodomestico;

    @ManyToOne
    @JoinColumn(name="idEndereco", referencedColumnName = "idEndereco")
    @JsonProperty
    @Getter
    @Setter
    private Endereco endereco;
    public Eletrodomestico() {
    }

    public Eletrodomestico(String nome, int potencia,
                           String modelo,
                           Endereco endereco,
                           LocalDateTime inicio_uso,
                           LocalDateTime fim_uso,
                           Double tempo_uso,
                           Boolean eletro_ligado) {
        this.nome = nome;
        this.potencia = potencia;
        this.modelo = modelo;
        this.idEletrodomestico = UUID.randomUUID();
        this.endereco = endereco;
        this.inicio_uso = inicio_uso;
        this.fim_uso = fim_uso;
        this.tempo_uso = tempo_uso;
        this.eletro_ligado = eletro_ligado;
    }



}
