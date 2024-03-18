package com.compose.account.infra.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class OpenApiConfig {

	@Value("${server.servlet.context-path}")
	private String contextPath;

	private final DomainProperty domainProperty;
	private static final String API_VERSION = "0.0.1";
	private static final String API_NAME = "ACCOUNT";
	private static final String API_DESCRIPTION = "Account API 명세서";
	private static final String API_DOCS = "/v3/api-docs/";
	private static final DomainProperty.DomainType PRIMARY_DOMAIN_TYPE = DomainProperty.DomainType.ACCOUNT;

	@Bean
	public GroupedOpenApi api() {
		return GroupedOpenApi.builder()
				.group(API_NAME)
				.pathsToMatch("/**")
				.build();
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.addServersItem(new Server().url(contextPath))
				.components(new Components()
				)
				.info(apiInfo());
	}

	private Info apiInfo() {
		return new Info().title(API_NAME).description(API_DESCRIPTION).version(API_VERSION);
	}

	@Bean
	@Primary
	public SwaggerUiConfigProperties swaggerUiConfigProperties(SwaggerUiConfigProperties swaggerUiConfigProperties) {
		swaggerUiConfigProperties.setUrlsPrimaryName(domainProperty.getDomainName(PRIMARY_DOMAIN_TYPE));
		swaggerUiConfigProperties.setOperationsSorter("alpha");
		swaggerUiConfigProperties.setTagsSorter("alpha");

		final Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> swaggerUrls = Arrays.stream(DomainProperty.DomainType.values())
				.filter(type -> type != PRIMARY_DOMAIN_TYPE)
				.map(type -> {
					AbstractSwaggerUiConfigProperties.SwaggerUrl url = new AbstractSwaggerUiConfigProperties.SwaggerUrl();
					url.setName(domainProperty.getDomainName(type));
					url.setUrl(domainProperty.getDomain(type) + API_DOCS);
					return url;
				}).collect(Collectors.toSet());
		swaggerUiConfigProperties.setUrls(swaggerUrls);

		return swaggerUiConfigProperties;
	}
}
