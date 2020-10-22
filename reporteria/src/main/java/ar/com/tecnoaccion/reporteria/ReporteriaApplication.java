package ar.com.tecnoaccion.reporteria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan(basePackages = {"ar.com.tecnoaccion.reporteria"})
@EntityScan("ar.com.tecnoaccion.reporteria.modelos")
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@EnableScheduling
@EnableAsync
@SpringBootApplication

public class ReporteriaApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ReporteriaApplication.class, args);
	}


}