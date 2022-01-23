package lsh.ext.gson.ext.java.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

	private static final TypeAdapterFactory instance = new LocalDateTypeAdapterFactory(null);

	private LocalDateTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(LocalDate.class, dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link LocalDateTypeAdapterFactory} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter instance
	 *
	 * @return An instance of {@link LocalDateTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalDateTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<LocalDate> create() {
		return LocalDateTypeAdapter.getInstance();
	}

	@Override
	protected TypeAdapter<LocalDate> create(final DateTimeFormatter dateTimeFormatter) {
		return LocalDateTypeAdapter.getInstance(dateTimeFormatter);
	}

}
