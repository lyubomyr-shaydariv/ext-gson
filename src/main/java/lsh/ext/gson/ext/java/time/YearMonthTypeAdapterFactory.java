package lsh.ext.gson.ext.java.time;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;

/**
 * Implements a type adapter factory for {@link YearMonth}.
 */
public final class YearMonthTypeAdapterFactory
		extends AbstractBaseTypeAdapterFactory<YearMonth> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapterFactory instance = new YearMonthTypeAdapterFactory(Adapter.getInstance());

	private YearMonthTypeAdapterFactory(final TypeAdapter<YearMonth> typeAdapter) {
		super(YearMonth.class, typeAdapter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link YearMonthTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new YearMonthTypeAdapterFactory(Adapter.getInstance(dateTimeFormatter));
	}

	/**
	 * A formatted type adapter for {@link YearMonth}.
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapter<YearMonth> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<YearMonth> instance = getInstance(new DateTimeFormatterBuilder()
				.appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
				.appendLiteral('-')
				.appendValue(ChronoField.MONTH_OF_YEAR, 2)
				.toFormatter()
		);

		private Adapter(final DateTimeFormatter dateTimeFormatter) {
			super(dateTimeFormatter, YearMonth::from);
		}

		public static TypeAdapter<YearMonth> getInstance(final DateTimeFormatter dateTimeFormatter) {
			return new Adapter(dateTimeFormatter)
					.nullSafe();
		}

	}

}
