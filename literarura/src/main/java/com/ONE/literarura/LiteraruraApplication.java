package com.ONE.literarura;

import com.ONE.literarura.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraruraApplication implements CommandLineRunner {

	private final Principal principal;

	public LiteraruraApplication(Principal principal) {
		this.principal = principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraruraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		principal.muestraElMenu();
	}
}
