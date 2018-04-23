package lsh.ext.gson.adapters.java8.time;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactory;

abstract class AbstractTemporalAccessorTypeAdapterFactory<T extends TemporalAccessor>
		extends AbstractTypeAdapterFactory<T> {

	@Nonnull
	private final Class<T> clazz;

	@Nullable
	private final DateTimeFormatter dateTimeFormatter;

	protected AbstractTemporalAccessorTypeAdapterFactory(@Nonnull final Class<T> clazz, @Nullable final DateTimeFormatter dateTimeFormatter) {
		this.clazz = clazz;
		this.dateTimeFormatter = dateTimeFormatter;
	}

	@Nonnull
	protected abstract TypeAdapter<T> create();

	@Nonnull
	protected abstract TypeAdapter<T> create(@Nonnull DateTimeFormatter dateTimeFormatter);

	@Override
	protected final boolean isSupported(@Nonnull final TypeToken<?> typeToken) {
		return typeToken.getRawType() == clazz;
	}

	@Nonnull
	@Override
	protected final TypeAdapter<T> createTypeAdapter(@Nonnull final Gson gson, @Nonnull final TypeToken<?> typeToken) {
		if ( dateTimeFormatter == null ) {
			return create();
		}
		return create(dateTimeFormatter);
	}

}
