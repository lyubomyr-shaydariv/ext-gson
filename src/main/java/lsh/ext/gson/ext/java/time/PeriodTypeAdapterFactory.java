package lsh.ext.gson.ext.java.time;

import java.time.Period;

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
 * Implements a type adapter factory for {@link Period}.
 *
 * @author Lyubomyr Shaydariv
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PeriodTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Period> {

	@Getter
	private static final TypeAdapterFactory instance = new PeriodTypeAdapterFactory();

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Period.class;
	}

	@Override
	protected TypeAdapter<Period> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return Adapter.getInstance();
	}

	/**
	 * A type adapter for {@link Period}.
	 *
	 * @author Lyubomyr Shaydariv
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter
			extends AbstractStringTypeAdapter<Period> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<Period> instance = new Adapter()
				.nullSafe();

		@Override
		protected String toString(final Period period) {
			return period.toString();
		}

		@Override
		protected Period fromString(final String text) {
			return Period.parse(text);
		}

	}

}
