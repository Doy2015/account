package com.compose.account.infra.uitls;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class NetworkUtil {

	private static final String HEADER_FORWARDED = "Forwarded";
	private static final String HEADER_X_FORWARDED_FOR = "X-FORWARDED-FOR";
	private static final String LOCAL_HOST = "127.0.0.1";
	private static final String REST = ",";

	public String getClientIp(HttpServletRequest request) {
		if (request.getHeader(HEADER_FORWARDED) == null) {
			return LOCAL_HOST;
		}
		String forwardedFor = Arrays.stream(request.getHeader(HEADER_FORWARDED).split(";")).findFirst()
				.map(f -> f.split(","))
				.map(f -> Arrays.stream(f).findFirst().orElse(StringUtils.EMPTY))
				.map(String::toLowerCase)
				.orElse(StringUtils.EMPTY);
		if (forwardedFor.contains("for=")) {
			return forwardedFor.replace("for=", "").trim();
		}

		return Optional.ofNullable(request.getHeader(HEADER_X_FORWARDED_FOR))
				.map(ip -> ip.split(REST))
				.map(List::of)
				.orElseGet(Collections::emptyList)
				.stream()
				.findFirst()
				.map(String::trim)
				.orElse(StringUtils.EMPTY);
	}
}
