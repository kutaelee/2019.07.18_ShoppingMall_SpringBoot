package com.shop.www;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.shop.www","com.hi.shop"})
// 기본적으로 @SpringBootApplication 밑으로 스캔하지만
// scanBasePackages를 선언하면 적혀진 경로를 스캔합니다.
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

}
