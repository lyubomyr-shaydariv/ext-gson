package lsh.ext.gson.ext.java.time.temporal;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractStringTypeAdapter;
import lsh.ext.gson.AbstractTypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractTemporalAccessorTypeAdapterFactory<T extends TemporalAccessor>
		extends AbstractTypeAdapterFactory<T> {

	private final Class<T> clazz;

	@Nullable
	private final DateTimeFormatter dateTimeFormatter;

	protected abstract TypeAdapter<T> create();

	protected abstract TypeAdapter<T> create(DateTimeFormatter dateTimeFormatter);

	@Override
	protected final boolean supports(final TypeToken<?> typeToken) {
		return typeToken.getRawType() == clazz;
	}

	@Override
	protected final TypeAdapter<T> createTypeAdapter(final Gson gson, final TypeToken<?> typeToken) {
		if ( dateTimeFormatter == null ) {
			return create();
		}
		return create(dateTimeFormatter);
	}

	@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
	protected abstract static class Adapter<T extends TemporalAccessor>
			extends AbstractStringTypeAdapter<T> {

		@Nullable
		private final DateTimeFormatter dateTimeFormatter;

		protected abstract T doFromString(String text);

		protected abstract T doFromString(String text, DateTimeFormatter formatter);

		@Override
		protected final T fromString(final String text) {
			if ( dateTimeFormatter == null ) {
				return doFromString(text);
			}
			return doFromString(text, dateTimeFormatter);
		}

		@Override
		protected final String toString(final T value) {
			if ( dateTimeFormatter == null ) {
				return value.toString();
			}
			return dateTimeFormatter.format(value);
		}

	}

}
