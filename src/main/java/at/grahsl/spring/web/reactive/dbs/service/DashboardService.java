package at.grahsl.spring.web.reactive.dbs.service;

import at.grahsl.spring.web.reactive.dbs.model.Exercise;
import at.grahsl.spring.web.reactive.dbs.model.ExerciseEvent;
import at.grahsl.spring.web.reactive.dbs.model.User;
import at.grahsl.spring.web.reactive.dbs.repository.CustomReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.Resource;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DashboardService {

    @Resource(name = "exercises")
    private CustomReactiveRepository<Exercise, String> exerciseRepo;

    @Resource(name = "users")
    private CustomReactiveRepository<User, String> userRepo;

    @Resource(name = "exercise-events")
    private CustomReactiveRepository<ExerciseEvent, String> exerciseEventRepo;

    private UnicastProcessor<ExerciseEvent> exerciseEventSource;
    private Flux<ExerciseEvent> exerciseEventFlux;

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardService.class);

    public DashboardService() {
        this.exerciseEventSource = UnicastProcessor.create();
        this.exerciseEventFlux = exerciseEventSource.publish().autoConnect();
        this.exerciseEventFlux.subscribe();
    }

    public Flux<User> readUsers() {
        return userRepo.findAll();
    }

    public Flux<Exercise> readExercises() {
        return exerciseRepo.findAll();
    }

    public Flux<ExerciseEvent> readExerciseEvents(String id) {
        return exerciseEventRepo.findAll()
                .filter(ee -> ee.getExerciseId().equals(id));
    }

    public Mono<Map<ExerciseEvent.UserAction,Long>> readExerciseEventStats(String id) {

        return exerciseEventRepo.findAll()
                .filter(ee -> ee.getExerciseId().equals(id))
                .collectList()
                .flatMap(list -> Mono.just(
                        list.stream().collect(
                                Collectors.groupingBy(ExerciseEvent::getWhat,
                                        Collectors.counting())
                                )
                        )
                ).flatMap(stats -> {
                            Stream.of(ExerciseEvent.UserAction.values())
                                    .forEach(ua -> stats.putIfAbsent(ua, 0L));
                            return Mono.just(stats);
                        }
                ).doOnNext(stats -> LOGGER.debug(stats.toString()));

    }

    public Mono<ExerciseEvent> saveExerciseEvent(ExerciseEvent event) {

        Mono<ExerciseEvent> result = exerciseRepo
                .findById(event.getExerciseId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException(
                                "error: no exercise found for id " + event.getExerciseId()
                )))
                .then(userRepo.findById(event.getUserId()))
                .switchIfEmpty(Mono.error(new IllegalArgumentException(
                                "error: no user found for id " + event.getUserId()
                )))
                .then(exerciseEventRepo.save(event));


        result.doOnNext(saved -> LOGGER.debug(saved.toString()))
                .doOnError(err -> LOGGER.error(err.getMessage()))
                .subscribe(exerciseEventSource::onNext);

        return result;

    }

    public Flux<ExerciseEvent> consumeExerciseEventStream(String id) {
        return exerciseEventFlux.filter(ee -> ee.getExerciseId().equals(id))
                .doOnNext(System.out::println);
    }

}
