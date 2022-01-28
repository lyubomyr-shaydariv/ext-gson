package lsh.ext.gson.ext.java.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link LocalTime}.
 *
 * @author Lyubomyr Shaydariv
 */
public final class LocalTimeTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<LocalTime> {

	private static final TypeAdapterFactory instance = new LocalTimeTypeAdapterFactory(null);

	private LocalTimeTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(LocalTime.class, dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link LocalTimeTypeAdapterFactory} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link LocalTimeTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<LocalTime> create() {
		return LocalTimeTypeAdapter.getInstance();
	}

	@Override
	protected TypeAdapter<LocalTime> create(final DateTimeFormatter dateTimeFormatter) {
		return LocalTimeTypeAdapter.getInstance(dateTimeFormatter);
	}

}
