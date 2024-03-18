package com.compose.account.domain.manager;

import com.compose.account.infra.enums.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ManagerRequest {
	private String name;
	private Long memberId;
	private RoleType roleType;

	public ManagerRequest(String name, Long memberId, RoleType roleType) {
		this.name = name;
		this.memberId = memberId;
		this.roleType = roleType;
	}
}
