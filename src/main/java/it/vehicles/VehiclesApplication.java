package it.vehicles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class VehiclesApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(VehiclesApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(VehiclesApplication.class, args);
//		System.getenv().forEach((k, v) -> System.out.println(k + ":" + v));
	}

}
