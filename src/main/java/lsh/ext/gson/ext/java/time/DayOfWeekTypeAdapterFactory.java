package lsh.ext.gson.ext.java.time;

import java.time.DayOfWeek;

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
 * Implements a type adapter factory for {@link DayOfWeek}.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DayOfWeekTypeAdapterFactory
		extends AbstractTypeAdapterFactory<DayOfWeek> {

	@Getter
	private static final TypeAdapterFactory instance = new DayOfWeekTypeAdapterFactory();

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == DayOfWeek.class;
	}

	@Override
	protected TypeAdapter<DayOfWeek> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return Adapter.getInstance();
	}

	/**
	 * A type adapter for {@link DayOfWeek}.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter
			extends AbstractStringTypeAdapter<DayOfWeek> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<DayOfWeek> instance = new Adapter()
				.nullSafe();

		@Override
		protected String toString(final DayOfWeek dayOfWeek) {
			return dayOfWeek.toString();
		}

		@Override
		protected DayOfWeek fromString(final String text) {
			return DayOfWeek.valueOf(text);
		}

	}

}
