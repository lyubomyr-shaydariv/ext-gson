package lsh.ext.gson.ext.java.time.temporal;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import javax.annotation.Nullable;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.adapters.AbstractStringTypeAdapter;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractTemporalAccessorTypeAdapter<T extends TemporalAccessor>
		extends AbstractStringTypeAdapter<T> {

	@Nullable
	private final DateTimeFormatter dateTimeFormatter;

	protected abstract T doFromString(final String text);

	protected abstract T doFromString(final String text, final DateTimeFormatter formatter);

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
