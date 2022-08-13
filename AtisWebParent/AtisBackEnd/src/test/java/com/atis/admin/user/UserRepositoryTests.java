package com.atis.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.atis.common.entity.Role;
import com.atis.common.entity.User;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userMimi = new User ("mimi@java.com", "mimi@12344", "Mimi", "Hapa");
		userMimi.addRole(roleAdmin);
		
		User savedUser = repo.save(userMimi);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateNewUserWithTwoRole() {
		
		User userWan = new User ("wanbis@mail.com", "wan36462", "Wan", "Bissaka");
		Role roleAssistant= new Role(4);
		Role roleDriver = new Role(5);
		
		userWan.addRole(roleAssistant);
		userWan.addRole(roleDriver);
		
		User savedUser = repo.save(userWan);
		
		assertThat(savedUser.getId()).isGreaterThan(0);
		
	}
	
	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		listUsers.forEach(user -> System.out.println());
	}
	
	@Test
	public void taestGetUserById() {
		User userMimi = repo.findById(1).get();
		System.out.println(userMimi);
		assertThat(userMimi).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetails() {
		User userMimi = repo.findById(1).get();
		userMimi.setEnabled(true);
		userMimi.setEmail("mimibwakks@yahoomail.com");
	}
	@Test
	public void testUpdateUserRoles() {
		User userWan = repo.findById(2).get();
		Role roleAssistant= new Role(4);
		Role roleDriver = new Role(5);
	
		userWan.getRoles().remove(roleAssistant);
		userWan.getRoles().remove(roleDriver);
		
		repo.save(userWan);

		
	}
	
	@Test
	public void testDeleteUser() {
		Integer userid = 2;
		repo.deleteById(userid);		
		
	}
	
	@Test
    public void testGetUserByEmail() {
		String email = "wanbis@mail.com";
		User user = repo.getUserByEmail(email);
		
		assertThat(user).isNotNull();
		}
	
	@Test
	public void testCountById() {
		Integer id = 1;
		Long countById = repo.countById(id);
		
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	@Test
	public void testDisableUser() {
		Integer id = 5;
		repo.updateEnabledStatus(id, false);
	}
	
	@Test
	public void testEnableUser() {
		Integer id = 5;
		repo.updateEnabledStatus(id, true);
	}
	
	@Test
	public void testListFirstPage() {
		int pageNumber = 1;
		int pageSize = 5;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		List<User> listUsers =  page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isEqualTo(pageSize);
		
	}
	
	@Test
	public void testSearchUsers() {
		String keyword = "Wan";
		
		int pageNumber = 0;
		int pageSize = 5;
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword, pageable);
		
		List<User> listUsers =  page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		
		assertThat(listUsers.size()).isGreaterThan(0);
	}
}
