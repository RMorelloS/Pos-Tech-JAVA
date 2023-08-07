package com.techchallenge.Monitoring_API.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="tb_usuario")
public class Usuario {
    @Getter
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID idUsuario;

    public Usuario(String loginUsuario, Set<Endereco> enderecosUsuario) {
        this.idUsuario = UUID.randomUUID();
        this.loginUsuario = loginUsuario;
        this.enderecosUsuario = enderecosUsuario;
    }

    public Usuario() {
    }

    @Getter
    @Setter
    @NotBlank(message = "Campo 'loginUsuario' é obrigatório e não pode estar vazio")
    private String loginUsuario;

    @OneToMany(mappedBy = "usuario", cascade= CascadeType.ALL)
    private Set<Endereco> enderecosUsuario = new HashSet<>();



}
