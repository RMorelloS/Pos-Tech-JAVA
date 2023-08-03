package com.techchallenge.Monitoring_API.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="tb_endereco")
public class Endereco {
    @Getter
    @Setter
    @NotBlank(message = "Campo 'rua' é obrigatório e não pode estar vazio")
    private String rua;
    @Getter
    @Setter
    @Min(value=0L, message = "Campo 'numero' deve ser um inteiro positivo")
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
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID idEndereco;

    @OneToMany(mappedBy = "endereco", cascade=CascadeType.ALL)
    private Set<Pessoa> pessoas = new HashSet<>();

    @OneToMany(mappedBy = "endereco", cascade=CascadeType.ALL)
    private Set<Eletrodomestico> eletrodomesticos = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco that = (Endereco) o;
        return Objects.equals(idEndereco, that.idEndereco);
    }

    public Endereco() {
    }

    public Endereco(String rua, int numero, String bairro,
                    String cidade, String estado,
                    Set<Pessoa> pessoas,
                    Set<Eletrodomestico> eletrodomesticos) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.idEndereco = UUID.randomUUID();
        this.pessoas = pessoas;
        this.eletrodomesticos = eletrodomesticos;
    }
}
