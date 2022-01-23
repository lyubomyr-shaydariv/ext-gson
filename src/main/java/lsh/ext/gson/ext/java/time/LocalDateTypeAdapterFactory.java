package lsh.ext.gson.ext.java.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link LocalDate}</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class LocalDateTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<LocalDate> {

	private static final TypeAdapterFactory defaultInstance = new LocalDateTypeAdapterFactory(null);

	private LocalDateTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(LocalDate.class, dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link LocalDateTypeAdapterFactory} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getDefaultInstance() {
		return defaultInstance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter instance
	 *
	 * @return An instance of {@link LocalDateTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return defaultInstance;
		}
		return new LocalDateTypeAdapterFactory(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalDate> create() {
		return LocalDateTypeAdapter.getDefaultInstance();
	}

	@Nonnull
	@Override
	protected TypeAdapter<LocalDate> create(@Nonnull final DateTimeFormatter dateTimeFormatter) {
		return LocalDateTypeAdapter.create(dateTimeFormatter);
	}

}
