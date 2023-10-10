package lsh.ext.gson.ext.java.time;

import java.time.Duration;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractStringTypeAdapter;

/**
 * A type adapter for {@link Duration}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DurationTypeAdapter
		extends AbstractStringTypeAdapter<Duration> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapter<Duration> instance = new DurationTypeAdapter()
			.nullSafe();

	@Override
	protected String toString(final Duration duration) {
		return duration.toString();
	}

	@Override
	protected Duration fromString(final String text) {
		return Duration.parse(text);
	}

}
