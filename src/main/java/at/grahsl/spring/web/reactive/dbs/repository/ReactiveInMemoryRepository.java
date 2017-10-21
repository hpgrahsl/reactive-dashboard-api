package at.grahsl.spring.web.reactive.dbs.repository;


import at.grahsl.spring.web.reactive.dbs.model.InMemoryEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class ReactiveInMemoryRepository<T extends InMemoryEntity<String>>
        implements CustomReactiveRepository<T,String> {

    private final Map<String,T> data = new LinkedHashMap<>();

    @Override
    public Mono<T> save(T entity) {
        if(entity.getId()==null) {
            entity.setId(UUID.randomUUID().toString());
        }
        data.put(entity.getId(),entity);
        return Mono.justOrEmpty(data.get(entity.getId()));
    }

    @Override
    public Flux<T> findAll() {
        return Flux.fromIterable(data.values());
    }

    @Override
    public Mono<T> findById(String id) {
        return Mono.justOrEmpty(data.get(id));
    }

    @Override
    public Mono<Long> count() {
        return Mono.just((long)data.size());
    }

}
