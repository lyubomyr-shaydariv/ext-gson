package lsh.ext.gson.ext.java.time;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.AbstractClassTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

public final class YearMonthTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<YearMonth> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	@SuppressWarnings("checkstyle:magicnumber")
	private static final TypeAdapter<YearMonth> instance = getInstance(new DateTimeFormatterBuilder()
			.appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
			.appendLiteral('-')
			.appendValue(ChronoField.MONTH_OF_YEAR, 2)
			.toFormatter()
	);

	private YearMonthTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter, YearMonth::from);
	}

	public static TypeAdapter<YearMonth> getInstance(final DateTimeFormatter dateTimeFormatter) {
		return new YearMonthTypeAdapter(dateTimeFormatter)
				.nullSafe();
	}

	public static final class Factory
			extends AbstractClassTypeAdapterFactory<YearMonth> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<YearMonth> instance = new Factory(YearMonthTypeAdapter.instance);

		private final TypeAdapter<YearMonth> typeAdapter;

		private Factory(final TypeAdapter<YearMonth> typeAdapter) {
			super(YearMonth.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<YearMonth> getInstance(final TypeAdapter<YearMonth> typeAdapter) {
			return new Factory(typeAdapter);
		}

		@Override
		protected TypeAdapter<YearMonth> createTypeAdapter(final Gson gson, final TypeToken<YearMonth> typeToken) {
			return typeAdapter;
		}

	}

}
