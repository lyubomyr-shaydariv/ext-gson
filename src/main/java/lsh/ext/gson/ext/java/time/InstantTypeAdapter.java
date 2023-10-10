package lsh.ext.gson.ext.java.time;

import java.time.Instant;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractStringTypeAdapter;

/**
 * A type adapter for {@link Instant}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class InstantTypeAdapter
		extends AbstractStringTypeAdapter<Instant> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapter<Instant> instance = new InstantTypeAdapter()
			.nullSafe();

	@Override
	protected String toString(final Instant instant) {
		return instant.toString();
	}

	@Override
	protected Instant fromString(final String text) {
		return Instant.parse(text);
	}

}
