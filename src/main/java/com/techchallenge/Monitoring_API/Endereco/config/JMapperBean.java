package com.techchallenge.Monitoring_API.Endereco.config;

import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;
import com.techchallenge.Monitoring_API.Endereco.controller.form.EnderecoUsuarioForm;
import com.techchallenge.Monitoring_API.Endereco.domain.EnderecoUsuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.googlecode.jmapper.api.JMapperAPI.attribute;

@Configuration
public class JMapperBean {
    @Bean
    public JMapper<EnderecoUsuario, EnderecoUsuarioForm> enderecoUsuarioMapper() {
        JMapperAPI jMapperAPI = new JMapperAPI()
                .add(JMapperAPI.mappedClass(EnderecoUsuario.class)
                        .add(attribute("rua").value("rua"))
                        .add(attribute("numero").value("numero"))
                        .add(attribute("bairro").value("bairro"))
                        .add(attribute("cidade").value("cidade"))
                        .add(attribute("estado").value("estado")));
        return new JMapper<>(EnderecoUsuario.class, EnderecoUsuarioForm.class, jMapperAPI);
    }
}

