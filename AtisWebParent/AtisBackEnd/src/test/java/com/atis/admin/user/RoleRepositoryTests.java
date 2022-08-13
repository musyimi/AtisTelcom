package com.atis.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.atis.common.entity.Role;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class RoleRepositoryTests {

	@Autowired
	private RoleRepository repo;
	
	@Test
	public void testCreateFirstRole() {
		
		Role roleAdmin = new Role("Admin", "Manage Everything");
		Role savedRole = repo.save(roleAdmin);
		assertThat(savedRole.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateRestRoles() {
		Role roleEditor = new Role("Editor", "Manage Website and system content");
		Role roleAssistant = new Role("Assistant", "Manage day to day running of the company");
		Role roleDriver = new Role("Driver", "Transport all company items and parcels as needed");
		Role roleCook = new Role("Cook", "Manage Kitchen activities");
		Role roleHr = new Role("Human Resource", "Make sure the company and government guide line are followed to the latter");
		Role roleOfficeEmployee = new Role("Employee", "Participate in the daily activities in the office");
		
		repo.saveAll(List.of(roleEditor,roleAssistant,roleDriver,roleCook,roleHr,roleOfficeEmployee));

	}
}
