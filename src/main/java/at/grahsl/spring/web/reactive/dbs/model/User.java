package at.grahsl.spring.web.reactive.dbs.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends InMemoryEntity<String> {

	private String name;

	private String email;

	public User(String id,String name,String email) {
		super(id);
		this.name = name;
		this.email = email;
	}

}
