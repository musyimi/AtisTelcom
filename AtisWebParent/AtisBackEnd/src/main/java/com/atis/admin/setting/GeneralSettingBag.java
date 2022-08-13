package com.atis.admin.setting;

import java.util.List;

import com.atis.common.entity.Setting;
import com.atis.common.entity.SettingBag;

public class GeneralSettingBag extends SettingBag {

	public GeneralSettingBag(List<Setting> listSettings) {
		super(listSettings);
	}
	
	public void updateSiteLogo(String value) {
		super.update("SITE_LOGO", value);
	}

}
