package br.com.trier.springvespertino;

import br.com.trier.springvespertino.services.*;
import br.com.trier.springvespertino.services.impl.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTest {
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}

	@Bean
	public ChampionshipService championshipService() {
		return new ChampionshipServiceImpl();
	}

	@Bean
	public CountryService countryService() {
		return new CountryServiceImpl();
	}

	@Bean
	public PilotRaceService pilotRaceService() {
		return new PilotRaceServiceImpl();
	}

	@Bean
	public PilotService pilotService() {
		return new PilotServiceImpl();
	}

	@Bean
	public RaceService raceService() {
		return new RaceServiceImpl();
	}

	@Bean
	public SpeedwayService speedwayService() {
		return new SpeedwayServiceImpl();
	}

}
