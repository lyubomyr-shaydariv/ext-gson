package lsh.ext.gson.ext.java.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link LocalDateTime}.
 *
 * @author Lyubomyr Shaydariv
 */
public final class LocalDateTimeTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<LocalDateTime> {

	private static final TypeAdapterFactory instance = new LocalDateTimeTypeAdapterFactory(null);

	private LocalDateTimeTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(LocalDateTime.class, dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link LocalDateTimeTypeAdapterFactory} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter instance
	 *
	 * @return An instance of {@link LocalDateTimeTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalDateTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<LocalDateTime> create() {
		return LocalDateTimeTypeAdapter.getInstance();
	}

	@Override
	protected TypeAdapter<LocalDateTime> create(final DateTimeFormatter dateTimeFormatter) {
		return LocalDateTimeTypeAdapter.getInstance(dateTimeFormatter);
	}

}
