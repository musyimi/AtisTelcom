package com.atis.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.atis.common.entity","com.atis.admin.user"})
public class AtisBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtisBackEndApplication.class, args);
	}

}
