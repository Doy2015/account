package com.compose.account.domain.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/manager")
public class ManagerController {

	private final ManagerService managerService;

	@GetMapping
	public List<Manager> findAll() {
		return managerService.findAll();
	}

	@GetMapping(value = "/{id}")
	public Manager findById(@PathVariable Long id) {
		return managerService.findById(id);
	}

	@PostMapping(value = "/create")
	public void createManager(@RequestBody ManagerRequest managerRequest) {
		final Manager manager = new Manager(managerRequest.getName(), managerRequest.getMemberId(), managerRequest.getRoleType());
		managerService.createManager(manager);
	}
}
