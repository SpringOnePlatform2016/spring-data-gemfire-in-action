package example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:client-cache.xml")
public class EventsExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsExampleApplication.class, args);
	}
}
