package at.grahsl.spring.web.reactive.dbs.repository;

import at.grahsl.spring.web.reactive.dbs.model.Exercise;
import at.grahsl.spring.web.reactive.dbs.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Resource(name="users")
    CustomReactiveRepository<User,String> userRepo;

    @Resource(name="exercises")
    CustomReactiveRepository<Exercise,String> exerciseRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializer.class);

    @Override
    public void run(String... args) throws Exception {

        //SAMPLE USER DATA
        Flux.just(new User("cce9fe86-a7a9-466e-ae71-a5eaa4e5d8bb","Hans-Peter","hanspeter@grahsl.at"),
                    new User("236da99d-4254-44e8-8559-e51fce5f9cdf","Manfred","manfred@softwarearchitekt.at")
        ).flatMap(userRepo::save)
                .subscribe(null,null,
                        () -> userRepo.findAll().subscribe(u -> LOGGER.info(u.toString()))
                );

        //SAMPLE EXERCISE DATA
        Flux.just(
                new Exercise("b7bf16e2-46fa-4411-9b1e-2a9e05d3be82","Angular Basics",
                        Arrays.asList(
                                new Exercise.Task("73e772e1-c88e-419f-9817-77b97995a0ea","this is task 1","description for the users...", Exercise.Task.Difficulty.EASY),
                                new Exercise.Task("7f73b152-32a9-4645-a422-137609ea6875","this is task 2a","description for the users...", Exercise.Task.Difficulty.EASY),
                                new Exercise.Task("e1c7da90-21cc-4de8-ae3e-8253e58f1f5c","this is task 2b","description for the users...", Exercise.Task.Difficulty.MEDIUM)
                        )
                ),
                new Exercise("5b46f5c2-196c-4a1e-a86e-8d946a9b4f99","Angular Advanced",
                        Arrays.asList(
                                new Exercise.Task("6192b158-7d25-4e45-97d7-0a3ba7110ed0","this is task A","description for the users...", Exercise.Task.Difficulty.HARD),
                                new Exercise.Task("3dd5d10a-f987-4a61-ab59-c50f1ca5cc3a","this is task B","description for the users...", Exercise.Task.Difficulty.MEDIUM),
                                new Exercise.Task("4b59d58d-d73c-498a-a19c-ad9855142170","this is task C","description for the users...", Exercise.Task.Difficulty.EASY)
                        )
                ),
                new Exercise("9da01173-f6da-4409-9177-4a5a18c6b484","Angular Reactive",
                        Arrays.asList(
                                new Exercise.Task("7bcb946d-9dcd-4d5e-be4f-64517973d334","this is task i","description for the users...", Exercise.Task.Difficulty.MEDIUM),
                                new Exercise.Task("9ccf3d43-0530-4146-b361-1f20ed5dd30e","this is task ii","description for the users...", Exercise.Task.Difficulty.HARD)
                        )
                )
        ).flatMap(exerciseRepo::save)
                .subscribe(null,null,
                        () -> exerciseRepo.findAll().subscribe(e -> LOGGER.info(e.toString()))
                );

    }
}
