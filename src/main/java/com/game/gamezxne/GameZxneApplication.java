package com.game.gamezxne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan("com.game.gamezxne.rps.model")
public class GameZxneApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameZxneApplication.class, args);
	}






}

