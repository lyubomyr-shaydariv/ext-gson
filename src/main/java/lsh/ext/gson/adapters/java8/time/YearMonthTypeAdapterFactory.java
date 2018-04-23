package lsh.ext.gson.adapters.java8.time;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;

public final class YearMonthTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<YearMonth> {

	private static final TypeAdapterFactory instance = new YearMonthTypeAdapterFactory(null);

	private YearMonthTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(YearMonth.class, dateTimeFormatter);
	}

	public static TypeAdapterFactory get() {
		return instance;
	}

	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new YearMonthTypeAdapterFactory(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected TypeAdapter<YearMonth> create() {
		return YearMonthTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected TypeAdapter<YearMonth> create(@Nonnull final DateTimeFormatter dateTimeFormatter) {
		return YearMonthTypeAdapter.get(dateTimeFormatter);
	}

}
