package lsh.ext.gson.adapters.java8.time;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;

public final class MonthDayTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<MonthDay> {

	private static final TypeAdapter<MonthDay> instance = new MonthDayTypeAdapter(null);

	private MonthDayTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	public static TypeAdapter<MonthDay> get() {
		return instance;
	}

	@Nonnull
	@Override
	protected MonthDay doFromString(@Nonnull final String string) {
		return MonthDay.parse(string);
	}

	@Nonnull
	@Override
	protected MonthDay doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return MonthDay.parse(string, formatter);
	}

}
