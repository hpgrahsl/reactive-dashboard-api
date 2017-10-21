package at.grahsl.spring.web.reactive.dbs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper=true)
public class ExerciseEvent extends InMemoryEntity<String> {

	public enum UserAction {
		VIEWED,
		STARTED,
		COMPLETED,
		ABORTED
	}

	@NonNull
	public final String exerciseId;

	@NonNull
	public final String userId;

	@NonNull
	@JsonFormat(shape=JsonFormat.Shape.STRING,
			pattern="yyyy-MM-dd'T'HH:mm:ss.SSS")
	public final LocalDateTime when;

	@NonNull
	public final UserAction what;

}
