package br.com.minsait.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("basicScheme",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic")))

                .info(new Info()
                        .title("App Cadastro de Contatos")
                        .description("Este aplicativo faz gerenciamento de cadastro de contatos")
                        .contact(new Contact().name("Wasse Lucas").email("wasselucas.dev@gmail.com").url(""))
                        .version("Versão 0.0.1-SNAPSHOT"));

    }

}
