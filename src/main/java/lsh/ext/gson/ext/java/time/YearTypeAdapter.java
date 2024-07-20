package lsh.ext.gson.ext.java.time;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ITypeAdapterFactory;

public final class YearTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<Year> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	@SuppressWarnings("checkstyle:magicnumber")
	private static final TypeAdapter<Year> instance = getInstance(new DateTimeFormatterBuilder()
			.parseLenient()
			.appendValue(ChronoField.YEAR, 1, 10, SignStyle.NORMAL)
			.toFormatter()
	);

	private YearTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter, Year::from);
	}

	public static TypeAdapter<Year> getInstance(final DateTimeFormatter dateTimeFormatter) {
		return new YearTypeAdapter(dateTimeFormatter)
				.nullSafe();
	}

	public static final class Factory
			extends AbstractBaseTypeAdapterFactory<Year> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<Year> instance = new Factory(YearTypeAdapter.getInstance());

		private Factory(final TypeAdapter<Year> typeAdapter) {
			super(Year.class, typeAdapter);
		}

		public static ITypeAdapterFactory<Year> getInstance(final DateTimeFormatter dateTimeFormatter) {
			return new Factory(YearTypeAdapter.getInstance(dateTimeFormatter));
		}

	}

}
