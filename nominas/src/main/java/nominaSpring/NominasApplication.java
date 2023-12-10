package nominaSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "config")
public class NominasApplication {

	public static void main(String[] args) {
		SpringApplication.run(NominasApplication.class, args);
	}

}
