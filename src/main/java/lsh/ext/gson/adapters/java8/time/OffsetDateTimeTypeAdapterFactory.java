package lsh.ext.gson.adapters.java8.time;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;

public final class OffsetDateTimeTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<OffsetDateTime> {

	private static final TypeAdapterFactory instance = new OffsetDateTimeTypeAdapterFactory(null);

	private OffsetDateTimeTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(OffsetDateTime.class, dateTimeFormatter);
	}

	public static TypeAdapterFactory get() {
		return instance;
	}

	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new OffsetDateTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected TypeAdapter<OffsetDateTime> create() {
		return OffsetDateTimeTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected TypeAdapter<OffsetDateTime> create(@Nonnull final DateTimeFormatter dateTimeFormatter) {
		return OffsetDateTimeTypeAdapter.get(dateTimeFormatter);
	}

}
