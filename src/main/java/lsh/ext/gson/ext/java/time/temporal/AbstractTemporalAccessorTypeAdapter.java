package lsh.ext.gson.ext.java.time.temporal;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractStringTypeAdapter;

public abstract class AbstractTemporalAccessorTypeAdapter<T extends TemporalAccessor>
		extends AbstractStringTypeAdapter<T> {

	@Nullable
	private final DateTimeFormatter dateTimeFormatter;

	protected AbstractTemporalAccessorTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}

	@Nonnull
	protected abstract T doFromString(@Nonnull final String string);

	@Nonnull
	protected abstract T doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter);

	@Nonnull
	@Override
	protected final T fromString(@Nonnull final String string) {
		if ( dateTimeFormatter == null ) {
			return doFromString(string);
		}
		return doFromString(string, dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected final String toString(@Nonnull final T value) {
		if ( dateTimeFormatter == null ) {
			return value.toString();
		}
		return dateTimeFormatter.format(value);
	}

}
