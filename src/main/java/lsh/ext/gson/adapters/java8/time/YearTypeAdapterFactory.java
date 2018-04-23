package lsh.ext.gson.adapters.java8.time;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;

public final class YearTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<Year> {

	private static final TypeAdapterFactory instance = new YearTypeAdapterFactory(null);

	private YearTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(Year.class, dateTimeFormatter);
	}

	public static TypeAdapterFactory get() {
		return instance;
	}

	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new YearTypeAdapterFactory(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected TypeAdapter<Year> create() {
		return YearTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected TypeAdapter<Year> create(@Nonnull final DateTimeFormatter dateTimeFormatter) {
		return YearTypeAdapter.get(dateTimeFormatter);
	}

}
