package lsh.ext.gson.ext.java.time;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link YearMonth}.
 *
 * @author Lyubomyr Shaydariv
 */
public final class YearMonthTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<YearMonth> {

	@Getter
	private static final TypeAdapterFactory instance = new YearMonthTypeAdapterFactory(null);

	private YearMonthTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(YearMonth.class, dateTimeFormatter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link YearMonthTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new YearMonthTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<YearMonth> create() {
		return YearMonthTypeAdapterFactory.Adapter.getInstance();
	}

	@Override
	protected TypeAdapter<YearMonth> create(final DateTimeFormatter dateTimeFormatter) {
		return YearMonthTypeAdapterFactory.Adapter.getInstance(dateTimeFormatter);
	}

	/**
	 * A type adapter for {@link YearMonth}.
	 *
	 * @author Lyubomyr Shaydariv
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapterFactory.Adapter<YearMonth> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<YearMonth> instance = new Adapter(null)
				.nullSafe();

		private Adapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
			super(dateTimeFormatter);
		}

		/**
		 * @param dateTimeFormatter
		 * 		Date/time formatter
		 *
		 * @return An instance of {@link Adapter} with a custom {@link DateTimeFormatter}.
		 */
		@SuppressFBWarnings("MS_EXPOSE_REP")
		public static TypeAdapter<YearMonth> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
			if ( dateTimeFormatter == null ) {
				return instance;
			}
			return new Adapter(dateTimeFormatter);
		}

		@Override
		protected YearMonth doFromString(final String text) {
			return YearMonth.parse(text);
		}

		@Override
		protected YearMonth doFromString(final String text, final DateTimeFormatter formatter) {
			return YearMonth.parse(text, formatter);
		}

	}

}
