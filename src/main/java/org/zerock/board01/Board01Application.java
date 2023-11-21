package org.zerock.board01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class Board01Application {

	public static void main(String[] args) {
		SpringApplication.run(Board01Application.class, args);
	}

}
