package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;

public final class LocalDateTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<LocalDate> {

	private static final TypeAdapterFactory instance = new LocalDateTypeAdapterFactory(null);

	private LocalDateTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(LocalDate.class, dateTimeFormatter);
	}

	public static TypeAdapterFactory get() {
		return instance;
	}

	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalDateTypeAdapterFactory(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalDate> create() {
		return LocalDateTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalDate> create(@Nonnull final DateTimeFormatter dateTimeFormatter) {
		return LocalDateTypeAdapter.get(dateTimeFormatter);
	}

}
