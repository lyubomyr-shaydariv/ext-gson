package lsh.ext.gson.ext.java.time;

import java.time.Instant;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractStringTypeAdapter;
import lsh.ext.gson.AbstractTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link Instant}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class InstantTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Instant> {

	/**
	 * An instance of {@link InstantTypeAdapterFactory}.
	 */
	@Getter
	private static final TypeAdapterFactory instance = new InstantTypeAdapterFactory();

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Instant.class;
	}

	@Override
	protected TypeAdapter<Instant> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return Adapter.getInstance();
	}

	/**
	 * A type adapter for {@link Instant}.
	 *
	 * @author Lyubomyr Shaydariv
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter
			extends AbstractStringTypeAdapter<Instant> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<Instant> instance = new Adapter()
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

}
