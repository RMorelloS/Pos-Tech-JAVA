package com.techchallenge.Monitoring_API.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="tb_endereco")
public class EnderecoUsuario {
    @Getter
    @Setter
    @NotBlank(message = "Campo 'rua' é obrigatório e não pode estar vazio")
    private String rua;
    @Getter
    @Setter
    //@Min(value=0L, message = "Campo 'numero' deve ser um inteiro positivo")
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnderecoUsuario that = (EnderecoUsuario) o;
        return Objects.equals(idEndereco, that.idEndereco);
    }

    public EnderecoUsuario() {
    }

    public EnderecoUsuario(String rua, int numero, String bairro, String cidade, String estado) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.idEndereco = UUID.randomUUID();
    }
}
