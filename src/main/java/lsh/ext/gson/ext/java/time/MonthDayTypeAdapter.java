package lsh.ext.gson.ext.java.time;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.AbstractClassTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

public final class MonthDayTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<MonthDay> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<MonthDay> instance = getInstance(new DateTimeFormatterBuilder()
			.appendLiteral("--")
			.appendValue(ChronoField.MONTH_OF_YEAR, 2)
			.appendLiteral('-')
			.appendValue(ChronoField.DAY_OF_MONTH, 2)
			.toFormatter()
	);

	private MonthDayTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter, MonthDay::from);
	}

	public static TypeAdapter<MonthDay> getInstance(final DateTimeFormatter dateTimeFormatter) {
		return new MonthDayTypeAdapter(dateTimeFormatter)
				.nullSafe();
	}

	public static final class Factory
			extends AbstractClassTypeAdapterFactory<MonthDay> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<MonthDay> instance = new Factory(MonthDayTypeAdapter.instance);

		private final TypeAdapter<MonthDay> typeAdapter;

		private Factory(final TypeAdapter<MonthDay> typeAdapter) {
			super(MonthDay.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<MonthDay> getInstance(final TypeAdapter<MonthDay> typeAdapter) {
			return new Factory(typeAdapter);
		}

		@Override
		protected TypeAdapter<MonthDay> createTypeAdapter(final Gson gson, final TypeToken<MonthDay> typeToken) {
			return typeAdapter;
		}

	}

}
