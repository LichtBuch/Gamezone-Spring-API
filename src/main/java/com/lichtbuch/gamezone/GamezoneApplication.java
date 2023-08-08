package com.lichtbuch.gamezone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan({
		"com.lichtbuch.gamezone.configs",
		"com.lichtbuch.gamezone.eventhandler"
})
public class GamezoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamezoneApplication.class, args);
	}

}
