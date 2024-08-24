package lsh.ext.gson.ext.java.time;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractCharSequenceTypeAdapter;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
abstract class AbstractTemporalAccessorTypeAdapter<T extends TemporalAccessor>
		extends AbstractCharSequenceTypeAdapter<T> {

	private final DateTimeFormatter dateTimeFormatter;
	private final TemporalQuery<? extends T> query;

	@Override
	protected final T fromCharSequence(final CharSequence cs) {
		return dateTimeFormatter.parse(cs, query);
	}

	@Override
	protected final CharSequence toCharSequence(final T value) {
		return dateTimeFormatter.format(value);
	}

}
