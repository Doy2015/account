package com.compose.account.system.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "회원 DTO")
public class MemberDto {
	@Schema(description = "ID")
	private Long id;

	@Schema(description = "이름")
	private String name;
}
