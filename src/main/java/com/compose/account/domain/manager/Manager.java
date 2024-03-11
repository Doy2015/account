package com.compose.account.domain.manager;

import com.compose.account.infra.config.RoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
public class Manager {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private Long memberId;

	@Enumerated(EnumType.STRING)
	@Column
	private RoleType roleType;

	public Manager(String name, Long memberId, RoleType roleType) {
		this.name = name;
		this.memberId = memberId;
		this.roleType = roleType;
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void updateRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
}
