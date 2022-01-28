package lsh.ext.gson.ext.java.time;

import java.time.Instant;

import com.google.gson.TypeAdapter;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

/**
 * A type adapter for {@link Instant}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class InstantTypeAdapter
		extends AbstractToStringStringTypeAdapter<Instant> {

	private static final TypeAdapter<Instant> instance = new InstantTypeAdapter();

	/**
	 * @return An instance of {@link InstantTypeAdapter}.
	 */
	public static TypeAdapter<Instant> getInstance() {
		return instance;
	}

	@Override
	protected Instant fromString(final String string) {
		return Instant.parse(string);
	}

}
