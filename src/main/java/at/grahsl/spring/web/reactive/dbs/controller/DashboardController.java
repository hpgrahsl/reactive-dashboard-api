package at.grahsl.spring.web.reactive.dbs.controller;

import at.grahsl.spring.web.reactive.dbs.model.Exercise;
import at.grahsl.spring.web.reactive.dbs.model.ExerciseEvent;
import at.grahsl.spring.web.reactive.dbs.model.User;
import at.grahsl.spring.web.reactive.dbs.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("dashboard/api/")
@CrossOrigin(origins = "*")
public class DashboardController {

    @Autowired
    public DashboardService dashboardService;

    @GetMapping("users")
    public Flux<User> getUsers() {
        return dashboardService.readUsers();
    }

    @GetMapping("exercises")
	public Flux<Exercise> getExercises() {
		return dashboardService.readExercises();
	}

	@PostMapping("exercises/events")
    public Mono<ResponseEntity<ExerciseEvent>> postExerciseEvent(@RequestBody ExerciseEvent payload) {
        return dashboardService.saveExerciseEvent(payload)
                .map(saved -> new ResponseEntity<>(saved, HttpStatus.CREATED))
                .onErrorResume(exc -> {
                    if(exc instanceof IllegalArgumentException) {
                        return Mono.just(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
                    }
                    return Mono.just(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
                });
    }

    @GetMapping("exercises/{id}/events")
    public Flux<ExerciseEvent> getExerciseEvents(@PathVariable String id) {
        return dashboardService.readExerciseEvents(id);
	}

    @GetMapping("exercises/{id}/eventstats")
    public Mono<Map<ExerciseEvent.UserAction,Long>> getExerciseEventStats(@PathVariable String id) {
        return dashboardService.readExerciseEventStats(id);
    }

    @GetMapping(value = "exercises/{id}/eventstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ExerciseEvent> getExerciseEventStream(@PathVariable String id) {
        return dashboardService.consumeExerciseEventStream(id);
    }

}
