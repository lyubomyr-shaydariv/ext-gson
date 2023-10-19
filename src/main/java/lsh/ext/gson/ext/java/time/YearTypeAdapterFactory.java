package lsh.ext.gson.ext.java.time;

import java.time.Year;
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
 * Implements a type adapter factory for {@link Year}.
 */
public final class YearTypeAdapterFactory
		extends AbstractBaseTypeAdapterFactory<Year> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapterFactory instance = new YearTypeAdapterFactory(Adapter.getInstance());

	private YearTypeAdapterFactory(final TypeAdapter<Year> typeAdapter) {
		super(Year.class, typeAdapter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link YearTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new YearTypeAdapterFactory(Adapter.getInstance(dateTimeFormatter));
	}

	/**
	 * A formatted type adapter for {@link Year}.
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapter<Year> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<Year> instance = getInstance(new DateTimeFormatterBuilder()
				.parseLenient()
				.appendValue(ChronoField.YEAR, 1, 10, SignStyle.NORMAL)
				.toFormatter()
		);

		private Adapter(final DateTimeFormatter dateTimeFormatter) {
			super(dateTimeFormatter, Year::from);
		}

		public static TypeAdapter<Year> getInstance(final DateTimeFormatter dateTimeFormatter) {
			return new Adapter(dateTimeFormatter)
					.nullSafe();
		}

	}

}
