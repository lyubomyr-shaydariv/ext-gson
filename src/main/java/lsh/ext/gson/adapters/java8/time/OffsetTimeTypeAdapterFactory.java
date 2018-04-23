package lsh.ext.gson.adapters.java8.time;

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;

public final class OffsetTimeTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<OffsetTime> {

	private static final TypeAdapterFactory instance = new OffsetTimeTypeAdapterFactory(null);

	private OffsetTimeTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(OffsetTime.class, dateTimeFormatter);
	}

	public static TypeAdapterFactory get() {
		return instance;
	}

	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new OffsetTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected TypeAdapter<OffsetTime> create() {
		return OffsetTimeTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected TypeAdapter<OffsetTime> create(@Nonnull final DateTimeFormatter dateTimeFormatter) {
		return OffsetTimeTypeAdapter.get(dateTimeFormatter);
	}

}
