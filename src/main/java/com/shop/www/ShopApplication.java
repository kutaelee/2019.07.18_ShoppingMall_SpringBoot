package com.shop.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"com.shop.www"})
// 기본적으로 @SpringBootApplication 밑으로 스캔하지만
// scanBasePackages를 선언하면 적혀진 경로를 스캔합니다.
public class ShopApplication {
	/*
	 * @Override protected SpringApplicationBuilder
	 * configure(SpringApplicationBuilder application) { return
	 * application.sources(ShopApplication.class); }
	 */

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

}
