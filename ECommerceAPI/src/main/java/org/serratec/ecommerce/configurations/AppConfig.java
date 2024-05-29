package org.serratec.ecommerce.configurations;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class AppConfig {

	@Value("${dominio.openapi.dev-url}")
	private String devUrl;

	@Value("${dominio.openapi.prod-url}")
	private String webUrl;

	@Bean
	OpenAPI myOpenAPI() {
		Server dbServer = new Server();
		dbServer.setDescription("URL do Server Desenvolvimento");
		dbServer.setUrl(devUrl);

		Server webServer = new Server();
		webServer.setDescription("URL do Server Desenvolvimento");
		webServer.setUrl(webUrl);

		Contact contato = new Contact();
		contato.setEmail("CONTATO@gmail.com");
		contato.setName("John Wick");

		License licence = new License().name("Apache License").url("https://....");

		Info info = new Info().title("API DO PROJETO DE ECOMMERCE").version("final").contact(contato).license(licence)
				.description("API PARA ESTUDOS");

		return new OpenAPI().info(info).servers(List.of(dbServer, webServer));
	}
}
