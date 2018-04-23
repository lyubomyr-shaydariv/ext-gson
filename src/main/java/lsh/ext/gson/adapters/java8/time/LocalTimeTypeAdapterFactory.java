package lsh.ext.gson.adapters.java8.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;

public final class LocalTimeTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<LocalTime> {

	private static final TypeAdapterFactory instance = new LocalTimeTypeAdapterFactory(null);

	private LocalTimeTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(LocalTime.class, dateTimeFormatter);
	}

	public static TypeAdapterFactory get() {
		return instance;
	}

	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalTime> create() {
		return LocalTimeTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalTime> create(@Nonnull final DateTimeFormatter dateTimeFormatter) {
		return LocalTimeTypeAdapter.get(dateTimeFormatter);
	}

}
