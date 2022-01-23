package lsh.ext.gson.ext.java.time;

import java.time.Duration;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

/**
 * <p>A type adapter for {@link Duration}.</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class DurationTypeAdapter
		extends AbstractToStringStringTypeAdapter<Duration> {

	private static final TypeAdapter<Duration> defaultInstance = new DurationTypeAdapter();

	private DurationTypeAdapter() {
	}

	/**
	 * @return An instance of {@link DurationTypeAdapter}.
	 */
	public static TypeAdapter<Duration> getDefaultInstance() {
		return defaultInstance;
	}

	@Override
	protected Duration fromString(final String string) {
		return Duration.parse(string);
	}

}
