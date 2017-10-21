package at.grahsl.spring.web.reactive.dbs.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomReactiveRepository<T,ID> {

    Mono<T> save(T entity);

    Flux<T> findAll();

    Mono<T> findById(ID id);

    Mono<Long> count();

}
