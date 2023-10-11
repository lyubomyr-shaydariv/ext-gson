package lsh.ext.gson.ext.java.time;

import java.time.Duration;

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
 * Implements a type adapter factory for {@link Duration}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DurationTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Duration> {

	@Getter
	private static final TypeAdapterFactory instance = new DurationTypeAdapterFactory();

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Duration.class;
	}

	@Override
	protected TypeAdapter<Duration> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return Adapter.getInstance();
	}

	/**
	 * A type adapter for {@link Duration}.
	 *
	 * @author Lyubomyr Shaydariv
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter
			extends AbstractStringTypeAdapter<Duration> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<Duration> instance = new Adapter()
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

}
