package com.petopia.petopia;

import com.petopia.petopia.models.entity_models.AccountStatus;
import com.petopia.petopia.models.entity_models.TokenStatus;
import com.petopia.petopia.repositories.AccountStatusRepo;
import com.petopia.petopia.repositories.TokenStatusRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class PetopiaApplication {

	private final TokenStatusRepo tokenStatusRepo;

	private final AccountStatusRepo accountStatusRepo;

	public static void main(String[] args) {
		SpringApplication.run(PetopiaApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDate(){
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {

				// init token status
				TokenStatus activeToken = tokenStatusRepo.save(
						TokenStatus.builder()
						.status("active")
						.build()
				);

				TokenStatus expiredToken = tokenStatusRepo.save(
						TokenStatus.builder()
								.status("expired")
								.build()
				);

				// init account status
				AccountStatus activeAccount = accountStatusRepo.save(
						AccountStatus.builder()
								.status("active")
								.build()
				);

				AccountStatus inactiveAccount = accountStatusRepo.save(
						AccountStatus.builder()
								.status("inactive")
								.build()
				);


			}
		};
	}

}
