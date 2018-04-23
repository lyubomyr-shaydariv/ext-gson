package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;

public final class LocalDateTimeTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<LocalDateTime> {

	private static final TypeAdapterFactory instance = new LocalDateTimeTypeAdapterFactory(null);

	private LocalDateTimeTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(LocalDateTime.class, dateTimeFormatter);
	}

	public static TypeAdapterFactory get() {
		return instance;
	}

	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalDateTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalDateTime> create() {
		return LocalDateTimeTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalDateTime> create(@Nonnull final DateTimeFormatter dateTimeFormatter) {
		return LocalDateTimeTypeAdapter.get(dateTimeFormatter);
	}

}
