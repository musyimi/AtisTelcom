package com.atis.admin.setting.county;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.atis.admin.setting.country.CountryRepository;
import com.atis.common.entity.Country;
import com.atis.common.entity.County;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CountyRestControllerTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	CountryRepository countryRepo;
	
	@Autowired
	CountyRepository countyRepo;
	
	@Test
	@WithMockUser(username = "dickson@mail.com", password="12345678", roles="ADMIN")
	public void testListByCountries() throws Exception {
		Integer countryId = 6 ;
		String url = "/counties/list_by_country/" + countryId;
		
		MvcResult result = mockMvc.perform(get(url))
				 .andExpect(status().isOk())
				 .andDo(print())
				 .andReturn();
		
		String jsonResponse = result.getResponse().getContentAsString();
		County[] counties = objectMapper.readValue(jsonResponse, County[].class);
		
		assertThat(counties).hasSizeGreaterThan(1);
	}
	
	@Test
	@WithMockUser(username = "dickson@mail.com", password="12345678", roles="ADMIN")
	public void testCreateState() throws Exception {
		String url = "/counties/save";
		Integer countryId = 6;
		Country country = countryRepo.findById(countryId).get();
		County county = new County("Nairobi", country);
		
	    MvcResult result = mockMvc.perform(post(url).contentType("application/json")
	    		.content(objectMapper.writeValueAsString(county))
	    		.with(csrf()))
	    		.andDo(print())
	    		.andExpect(status().isOk())
	    		.andReturn();
	    
	    String response = result.getResponse().getContentAsString();
	    Integer countyId = Integer.parseInt(response);
	    Optional<County> findById = countyRepo.findById(countyId);
	    
	    assertThat(findById.isPresent());
	    		
				
				
	}
	
	@Test
	@WithMockUser(username = "dickson@mail.com", password="12345678", roles="ADMIN")
	public void testUpdateState() throws Exception {
		String url = "/counties/save";
		Integer countyId = 3;
		String countyName = "Mombasa";
		
		County county = countyRepo.findById(countyId).get();
		county.setName(countyName);
		
	    mockMvc.perform(post(url).contentType("application/json")
	    		.content(objectMapper.writeValueAsString(county))
	    		.with(csrf()))
	    		.andDo(print())
	    		.andExpect(status().isOk())
	    		.andExpect(content().string(String.valueOf(countyId)));
	    
	    Optional<County> findById = countyRepo.findById(countyId);
	    assertThat(findById.isPresent());
	    
	    County updatedCounty = findById.get();
	    assertThat(updatedCounty.getName()).isEqualTo(countyName);
	    		
				
				
	}
	
	@Test
	@WithMockUser(username = "dickson@mail.com", password="12345678", roles="ADMIN")
	public void testDeleteState() throws Exception {
	
		Integer countyId = 2;
		String url = "/counties/delete/" + countyId;
		
	    mockMvc.perform(get(url)).andExpect(status().isOk());
	    		    
	    Optional<County> findById = countyRepo.findById(countyId);
	    assertThat(findById).isNotPresent();
	    

	    		
				
				
	}

}
