package at.grahsl.spring.web.reactive.dbs;

import at.grahsl.spring.web.reactive.dbs.model.Exercise;
import at.grahsl.spring.web.reactive.dbs.model.ExerciseEvent;
import at.grahsl.spring.web.reactive.dbs.model.User;
import at.grahsl.spring.web.reactive.dbs.repository.CustomReactiveRepository;
import at.grahsl.spring.web.reactive.dbs.repository.ReactiveInMemoryRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DashboardApp {

	@Bean(name="exercises")
	CustomReactiveRepository exerciseRepo() {
		return new ReactiveInMemoryRepository<Exercise>();
	}

	@Bean(name="users")
	CustomReactiveRepository userRepo() {
		return new ReactiveInMemoryRepository<User>();
	}

	@Bean(name="exercise-events")
	CustomReactiveRepository exerciseEventsRepo() {
		return new ReactiveInMemoryRepository<ExerciseEvent>();
	}

	public static void main(String[] args) {
		SpringApplication.run(DashboardApp.class, args);
	}

}
