package lsh.ext.gson.ext.java.time;

import java.time.Instant;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

/**
 * <p>A type adapter for {@link Instant}.</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class InstantTypeAdapter
		extends AbstractToStringStringTypeAdapter<Instant> {

	private static final TypeAdapter<Instant> defaultInstance = new InstantTypeAdapter();

	private InstantTypeAdapter() {
	}

	/**
	 * @return An instance of {@link InstantTypeAdapter}.
	 */
	public static TypeAdapter<Instant> getDefaultInstance() {
		return defaultInstance;
	}

	@Nonnull
	@Override
	protected Instant fromString(@Nonnull final String string) {
		return Instant.parse(string);
	}

}
