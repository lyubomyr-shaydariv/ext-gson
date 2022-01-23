package lsh.ext.gson.ext.java.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link LocalTime}</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class LocalTimeTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<LocalTime> {

	private static final TypeAdapterFactory defaultInstance = new LocalTimeTypeAdapterFactory(null);

	private LocalTimeTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(LocalTime.class, dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link LocalTimeTypeAdapterFactory} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getDefaultInstance() {
		return defaultInstance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link LocalTimeTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return defaultInstance;
		}
		return new LocalTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalTime> create() {
		return LocalTimeTypeAdapter.getDefaultInstance();
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalTime> create(@Nonnull final DateTimeFormatter dateTimeFormatter) {
		return LocalTimeTypeAdapter.create(dateTimeFormatter);
	}

}
