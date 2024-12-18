package com.gen.fitness.configuration;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI springBlogPessoalOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("GenFitness")
                .description("O projeto GenFitness é uma plataforma digital que facilita o acesso a pacotes e planos de academias, oferecendo uma experiência completa de saúde e bem-estar. \n\n" +
                             "Conecta três grupos principais: \n" +
                             "- Consumidores buscando melhorar sua saúde; \n" +
                             "- Academias ampliando sua base de clientes; \n" +
                             "- Profissionais, como nutricionistas e massagistas, oferecendo suporte contínuo.\n\n" +
                             "**Contatos principais:**\n" +
                             "1. Raquel M. (raquelmorabito@hotmail.com | [GitHub](https://github.com/raquelmorabito))\n" +
                             "2. Carla J. (carla.jemaitis@gmail.com | [GitHub](https://github.com/carlajemaitis))\n" +
                             "3. Gustavo F. (gustavo.custodio55@hotmail.com | [GitHub](https://github.com/Gustav0Felipe))\n" +
                             "4. Lucas U. (lucasunzaga87@gmail.com | [GitHub](https://github.com/lucasunzaga))")
                .version("v0.0.1")
                .license(new License()
                    .name("Me Gusta Racalu")
                    .url("https://github.com/meGustaRacalu/Fitness"))
                .contact(new Contact()
                    .name("Raquel M.")
                    .url("https://github.com/raquelmorabito")
                    .email("megustaracalu@gmail.com")))
            .externalDocs(new ExternalDocumentation()
                .description("Github do Projeto")
                .url("https://github.com/meGustaRacalu"));
    }
    @Bean
	OpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {

		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

				ApiResponses apiResponses = operation.getResponses();

				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto Persistido!"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto Excluído!"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na Requisição!"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso Não Autorizado!"));
				apiResponses.addApiResponse("403", createApiResponse("Acesso Proibido!"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto Não Encontrado!"));
				apiResponses.addApiResponse("500", createApiResponse("Erro na Aplicação!"));

			}));
		};
	}

	private ApiResponse createApiResponse(String message) {

		return new ApiResponse().description(message);

	}
}
