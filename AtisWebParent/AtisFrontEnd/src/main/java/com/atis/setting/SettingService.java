package com.atis.setting;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atis.common.entity.Setting;
import com.atis.common.entity.SettingCategory;

@Service
public class SettingService {
	
	@Autowired
	private SettingRepository repo; 
	

	
	public List<Setting> getGeneralSettings() {
	
		return repo.findByTwoCategories(SettingCategory.GENERAL, SettingCategory.GENERAL); 
	}
	
	public EmailSettingBag getEmailSettings() {
		List<Setting> settings = repo.findByCategory(SettingCategory.MAIL_SERVER);
		settings.addAll(repo.findByCategory(SettingCategory.MAIL_TEMPLATES));
		
		return new EmailSettingBag(settings);
	}
	


}
