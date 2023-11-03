package lsh.ext.gson.ext.java.time;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractStringTypeAdapter;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
abstract class AbstractTemporalAccessorTypeAdapter<T extends TemporalAccessor>
		extends AbstractStringTypeAdapter<T> {

	private final DateTimeFormatter dateTimeFormatter;
	private final TemporalQuery<? extends T> query;

	@Override
	protected final T fromString(final String text) {
		return dateTimeFormatter.parse(text, query);
	}

	@Override
	protected final String toString(final T value) {
		return dateTimeFormatter.format(value);
	}

}
