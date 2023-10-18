package lsh.ext.gson.ext.java.time;

import java.time.Month;

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
 * Implements a type adapter factory for {@link Month}.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MonthTypeAdapterFactory
		extends AbstractTypeAdapterFactory<Month> {

	@Getter
	private static final TypeAdapterFactory instance = new MonthTypeAdapterFactory();

	@Override
	protected boolean supports(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == Month.class;
	}

	@Override
	protected TypeAdapter<Month> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		return Adapter.getInstance();
	}

	/**
	 * A type adapter for {@link Month}.
	 */
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Adapter
			extends AbstractStringTypeAdapter<Month> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<Month> instance = new Adapter()
				.nullSafe();

		@Override
		protected String toString(final Month month) {
			return month.toString();
		}

		@Override
		protected Month fromString(final String text) {
			return Month.valueOf(text);
		}

	}

}
