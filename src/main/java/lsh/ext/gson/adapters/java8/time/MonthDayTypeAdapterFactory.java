package lsh.ext.gson.adapters.java8.time;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;

public final class MonthDayTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<MonthDay> {

	private static final TypeAdapterFactory instance = new MonthDayTypeAdapterFactory(null);

	private MonthDayTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(MonthDay.class, dateTimeFormatter);
	}

	public static TypeAdapterFactory get() {
		return instance;
	}

	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new MonthDayTypeAdapterFactory(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected TypeAdapter<MonthDay> create() {
		return MonthDayTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected TypeAdapter<MonthDay> create(@Nonnull final DateTimeFormatter dateTimeFormatter) {
		return MonthDayTypeAdapter.get(dateTimeFormatter);
	}

}
