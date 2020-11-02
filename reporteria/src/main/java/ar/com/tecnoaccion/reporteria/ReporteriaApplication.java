package ar.com.tecnoaccion.reporteria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"ar.com.tecnoaccion"})
@EntityScan("ar.com.tecnoaccion")
@SpringBootApplication

public class ReporteriaApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(ReporteriaApplication.class, args);
	}


}