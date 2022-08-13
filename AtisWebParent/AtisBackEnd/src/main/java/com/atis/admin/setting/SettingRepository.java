package com.atis.admin.setting;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.atis.common.entity.Setting;
import com.atis.common.entity.SettingCategory;

public interface SettingRepository extends CrudRepository<Setting, String> {

	public List<Setting> findByCategory(SettingCategory category);
}
