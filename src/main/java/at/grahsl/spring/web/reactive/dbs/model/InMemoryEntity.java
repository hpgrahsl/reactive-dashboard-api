package at.grahsl.spring.web.reactive.dbs.model;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class InMemoryEntity<T> {

    @Getter @Setter private T id;

}
