package Grupo4.ProyectoDesarrollo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AvanceProyectoApplication {
	public static void main(String[] args) {
		SpringApplication.run(AvanceProyectoApplication.class, args);
	}
}
