package lsh.ext.gson.ext.java.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link LocalDateTime}</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
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
	public static TypeAdapterFactory get() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter instance
	 *
	 * @return An instance of {@link LocalDateTimeTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalDateTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalDateTime> create() {
		return LocalDateTimeTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalDateTime> create(@Nonnull final DateTimeFormatter dateTimeFormatter) {
		return LocalDateTimeTypeAdapter.get(dateTimeFormatter);
	}

}
