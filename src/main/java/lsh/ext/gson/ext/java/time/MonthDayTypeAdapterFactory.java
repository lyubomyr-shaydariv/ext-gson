package lsh.ext.gson.ext.java.time;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link MonthDay}.
 */
public final class MonthDayTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<MonthDay> {

	@Getter
	private static final TypeAdapterFactory instance = new MonthDayTypeAdapterFactory(null);

	private MonthDayTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(MonthDay.class, dateTimeFormatter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link MonthDayTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new MonthDayTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<MonthDay> create() {
		return MonthDayTypeAdapterFactory.Adapter.getInstance();
	}

	@Override
	protected TypeAdapter<MonthDay> create(final DateTimeFormatter dateTimeFormatter) {
		return MonthDayTypeAdapterFactory.Adapter.getInstance(dateTimeFormatter);
	}

	/**
	 * A type adapter for {@link MonthDay}.
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapterFactory.Adapter<MonthDay> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<MonthDay> instance = new Adapter(null)
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
		public static TypeAdapter<MonthDay> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
			if ( dateTimeFormatter == null ) {
				return instance;
			}
			return new Adapter(dateTimeFormatter);
		}

		@Override
		protected MonthDay doFromString(final String text) {
			return MonthDay.parse(text);
		}

		@Override
		protected MonthDay doFromString(final String text, final DateTimeFormatter formatter) {
			return MonthDay.parse(text, formatter);
		}

	}

}
