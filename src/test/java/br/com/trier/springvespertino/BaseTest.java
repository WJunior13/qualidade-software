package br.com.trier.springvespertino;

import br.com.trier.springvespertino.services.ChampionshipService;
import br.com.trier.springvespertino.services.impl.ChampionshipServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.springvespertino.services.UserService;
import br.com.trier.springvespertino.services.impl.UserServiceImpl;

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

}
