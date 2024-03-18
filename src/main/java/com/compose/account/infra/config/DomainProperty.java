package com.compose.account.infra.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Configuration
@ConfigurationProperties(prefix = "compose.domain")
public class DomainProperty {

	private String system;
	private String account;

	public enum DomainType {
		SYSTEM,
		ACCOUNT
	}

	public String getDomain(DomainType type) {
		return switch (type) {
			case SYSTEM -> this.system;
			case ACCOUNT -> this.account;
		};
	}

	public String getDomainName(DomainType type) {
		return type.toString().toLowerCase();
	}
}
