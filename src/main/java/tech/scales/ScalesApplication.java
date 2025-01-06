package tech.scales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScalesApplication {
	public static void main(String[] args) {
		SpringApplication.run(ScalesApplication.class, args);
	}
}