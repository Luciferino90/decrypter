package it.usuratonkachi.crypto.decrypter;

import it.usuratonkachi.crypto.decrypter.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(Configuration.class)
public class DecrypterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DecrypterApplication.class, args);
	}

}
