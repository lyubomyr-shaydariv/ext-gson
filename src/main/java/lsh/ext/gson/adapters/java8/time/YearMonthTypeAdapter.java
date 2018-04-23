package lsh.ext.gson.adapters.java8.time;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;

public final class YearMonthTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<YearMonth> {

	private static final TypeAdapter<YearMonth> instance = new YearMonthTypeAdapter(null);

	private YearMonthTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	public static TypeAdapter<YearMonth> get() {
		return instance;
	}

	public static TypeAdapter<YearMonth> get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new YearMonthTypeAdapter (dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected YearMonth doFromString(@Nonnull final String string) {
		return YearMonth.parse(string);
	}

	@Nonnull
	@Override
	protected YearMonth doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return YearMonth.parse(string, formatter);
	}

}
