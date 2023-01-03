package lsh.ext.gson.ext.java.time;

import java.time.Instant;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
	@SuppressFBWarnings("MS_EXPOSE_REP")
	public static TypeAdapter<Instant> getInstance() {
		return instance;
	}

	@Override
	protected Instant fromString(final String text) {
		return Instant.parse(text);
	}

}
