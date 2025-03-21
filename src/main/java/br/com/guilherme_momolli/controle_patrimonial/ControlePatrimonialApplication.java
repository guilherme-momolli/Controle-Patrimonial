package br.com.guilherme_momolli.controle_patrimonial;

import br.com.guilherme_momolli.controle_patrimonial.configs.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableConfigurationProperties({
		FileStorageConfig.class
})
@SpringBootApplication
public class ControlePatrimonialApplication {



	public static void main(String[] args) {
		SpringApplication.run(ControlePatrimonialApplication.class, args);
	}

}
