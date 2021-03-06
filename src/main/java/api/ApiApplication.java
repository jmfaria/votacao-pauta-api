package api;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiApplication {
	
	@PostConstruct
    void started() {
      TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
    }

	public static void main(String[] args) {
		
		SpringApplication.run(ApiApplication.class, args);			
	}
}
