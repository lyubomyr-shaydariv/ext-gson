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

	protected abstract T doFromString(final String string);

	protected abstract T doFromString(final String string, final DateTimeFormatter formatter);

	@Override
	protected final T fromString(final String string) {
		if ( dateTimeFormatter == null ) {
			return doFromString(string);
		}
		return doFromString(string, dateTimeFormatter);
	}

	@Override
	protected final String toString(final T value) {
		if ( dateTimeFormatter == null ) {
			return value.toString();
		}
		return dateTimeFormatter.format(value);
	}

}
