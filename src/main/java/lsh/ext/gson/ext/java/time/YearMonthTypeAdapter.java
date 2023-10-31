package lsh.ext.gson.ext.java.time;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ITypeAdapterFactory;

public final class YearMonthTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<YearMonth> {

	@Getter(onMethod_ = @SuppressFBWarnings("MS_EXPOSE_REP"))
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
			extends AbstractBaseTypeAdapterFactory<YearMonth> {

		@Getter(onMethod_ = @SuppressFBWarnings("MS_EXPOSE_REP"))
		private static final ITypeAdapterFactory<YearMonth> instance = new Factory(YearMonthTypeAdapter.getInstance());

		private Factory(final TypeAdapter<YearMonth> typeAdapter) {
			super(YearMonth.class, typeAdapter);
		}

		public static ITypeAdapterFactory<YearMonth> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
			if ( dateTimeFormatter == null ) {
				return instance;
			}
			return new Factory(YearMonthTypeAdapter.getInstance(dateTimeFormatter));
		}

	}

}
