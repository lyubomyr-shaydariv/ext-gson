package lsh.ext.gson.ext.java.time;

import java.time.Duration;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

/**
 * A type adapter for {@link Duration}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DurationTypeAdapter
		extends AbstractToStringStringTypeAdapter<Duration> {

	private static final TypeAdapter<Duration> instance = new DurationTypeAdapter();

	/**
	 * @return An instance of {@link DurationTypeAdapter}.
	 */
	@SuppressFBWarnings("MS_EXPOSE_REP")
	public static TypeAdapter<Duration> getInstance() {
		return instance;
	}

	@Override
	protected Duration fromString(final String string) {
		return Duration.parse(string);
	}

}
