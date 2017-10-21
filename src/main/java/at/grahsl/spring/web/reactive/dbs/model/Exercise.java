package at.grahsl.spring.web.reactive.dbs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Exercise extends InMemoryEntity<String> {

	@Data
	@AllArgsConstructor
	public static class Task {

		public enum Difficulty {
			EASY,
			MEDIUM,
			HARD
		}

		private String id;

		private String title;

		private String description;

		private Difficulty level;

	}

	private String title;

	private List<Task> tasks;

	public Exercise(String id, String title, List<Task> tasks) {
		super(id);
		this.title = title;
		this.tasks = tasks;
	}

}
