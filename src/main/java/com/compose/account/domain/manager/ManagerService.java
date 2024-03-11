package com.compose.account.domain.manager;

import com.compose.account.infra.config.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManagerService {

	private final ManagerRepository managerRepository;

	public Manager findById(Long id) {
		return managerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found manager"));
	}

	public List<Manager> findAll() {
		return managerRepository.findAll();
	}

	@Transactional
	public void createManager(Manager member) {
		managerRepository.save(member);
	}

	@Transactional
	public void updateManager(Long id, String name, RoleType roleType) {
		final Manager findManager = findById(id);
		findManager.updateName(name);
		findManager.updateRoleType(roleType);
	}
}
