package com.atis.admin.setting.county;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.atis.common.entity.Country;
import com.atis.common.entity.County;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CountyRepositoryTests {
	@Autowired
	private CountyRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void testCreateCountiesInKenya() {
		Integer countryId = 1;
		Country country = entityManager.find(Country.class, countryId);
		
		County county = repo.save(new County("Nairobi", country));
		
		assertThat(county).isNotNull();
		assertThat(county.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListCountiesByCountry() {
		Integer countryId = 1;
		Country country = entityManager.find(Country.class, countryId);
		List<County> listCounties = repo.findByCountryOrderByNameAsc(country);
		
		listCounties.forEach(System.out::println);
		
		assertThat(listCounties.size()).isGreaterThan(0);
	}
	
	@Test
	public void testUpdateCounty() {
		Integer countyId = 1;
		String countyName = "Mombasa";
		County county = repo.findById(countyId).get();
		
		county.setName(countyName);
		County updatedCounty = repo.save(county);
		
		assertThat(updatedCounty.getName()).isEqualTo(countyName);
	}
	
	@Test
	public void testDeleteCounty() {
		Integer countyId = 1;
		repo.deleteById(countyId);
		
		Optional<County> findById = repo.findById(countyId);
		assertThat(findById.isEmpty());
	}

}
