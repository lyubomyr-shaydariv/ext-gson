package lsh.ext.gson.ext.java.time;

import java.time.Duration;

import com.google.gson.TypeAdapter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

/**
 * <p>A type adapter for {@link Duration}.</p>
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
	public static TypeAdapter<Duration> getInstance() {
		return instance;
	}

	@Override
	protected Duration fromString(final String string) {
		return Duration.parse(string);
	}

}
