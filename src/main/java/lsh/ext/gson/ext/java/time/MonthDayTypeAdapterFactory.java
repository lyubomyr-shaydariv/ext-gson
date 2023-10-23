package lsh.ext.gson.ext.java.time;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ITypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link MonthDay}.
 */
public final class MonthDayTypeAdapterFactory
		extends AbstractBaseTypeAdapterFactory<MonthDay>
		implements ITypeAdapterFactory<MonthDay> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final ITypeAdapterFactory<MonthDay> instance = new MonthDayTypeAdapterFactory(Adapter.getInstance());

	private MonthDayTypeAdapterFactory(final TypeAdapter<MonthDay> typeAdapter) {
		super(MonthDay.class, typeAdapter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link MonthDayTypeAdapterFactory}.
	 */
	public static ITypeAdapterFactory<MonthDay> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new MonthDayTypeAdapterFactory(Adapter.getInstance(dateTimeFormatter));
	}

	/**
	 * A formatted type adapter for {@link MonthDay}.
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapter<MonthDay> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<MonthDay> instance = getInstance(new DateTimeFormatterBuilder()
				.appendLiteral("--")
				.appendValue(ChronoField.MONTH_OF_YEAR, 2)
				.appendLiteral('-')
				.appendValue(ChronoField.DAY_OF_MONTH, 2)
				.toFormatter()
		);

		private Adapter(final DateTimeFormatter dateTimeFormatter) {
			super(dateTimeFormatter, MonthDay::from);
		}

		public static TypeAdapter<MonthDay> getInstance(final DateTimeFormatter dateTimeFormatter) {
			return new Adapter(dateTimeFormatter)
					.nullSafe();
		}

	}

}
