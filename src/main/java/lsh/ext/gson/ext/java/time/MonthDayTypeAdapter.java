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

public final class MonthDayTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<MonthDay> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
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
			extends AbstractBaseTypeAdapterFactory<MonthDay> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final ITypeAdapterFactory<MonthDay> instance = new Factory(MonthDayTypeAdapter.getInstance());

		private Factory(final TypeAdapter<MonthDay> typeAdapter) {
			super(MonthDay.class, typeAdapter);
		}

		public static ITypeAdapterFactory<MonthDay> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
			if ( dateTimeFormatter == null ) {
				return instance;
			}
			return new Factory(MonthDayTypeAdapter.getInstance(dateTimeFormatter));
		}

	}

}
