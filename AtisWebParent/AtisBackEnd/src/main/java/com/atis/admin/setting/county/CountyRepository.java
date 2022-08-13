package com.atis.admin.setting.county;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.atis.common.entity.Country;
import com.atis.common.entity.County;

public interface CountyRepository extends CrudRepository<County, Integer> {
	
	public List<County> findByCountryOrderByNameAsc(Country country);

}
