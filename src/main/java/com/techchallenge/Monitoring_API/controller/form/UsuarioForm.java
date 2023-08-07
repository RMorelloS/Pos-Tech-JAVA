package com.techchallenge.Monitoring_API.controller.form;

import com.techchallenge.Monitoring_API.domain.Endereco;
import com.techchallenge.Monitoring_API.domain.Pessoa;
import com.techchallenge.Monitoring_API.domain.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UsuarioForm {

    @Getter
    @Setter
    @NotBlank(message = "Campo 'loginUsuario' é obrigatório e não pode estar vazio")
    private String loginUsuario;
    @Getter
    @Setter
    @OneToMany(mappedBy = "usuario", cascade= CascadeType.ALL)
    private Set<Endereco> enderecosUsuario = new HashSet<>();
    public Usuario toUsuario(UsuarioForm usuarioForm) {
        return new Usuario(usuarioForm.getLoginUsuario(),
                usuarioForm.getEnderecosUsuario());
    }

}
