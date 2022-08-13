package com.atis.setting;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.atis.common.entity.Country;
import com.atis.common.entity.County;
import com.atis.common.entity.CountyDTO;

@RestController
public class CountyRestController {
	
	@Autowired
	private CountyRepository repo;
	
	@GetMapping("/settings/list_counties_by_country/{id}")
	public List<CountyDTO> listByCountry(@PathVariable("id") Integer countryId) {
		List<County> listCounties = repo.findByCountryOrderByNameAsc(new Country());
		List<CountyDTO> result = new ArrayList<>();
		
		for (County county : listCounties) {
			result.add(new CountyDTO(county.getId(), county.getName()));
		}
		
		return result;
	}


}
