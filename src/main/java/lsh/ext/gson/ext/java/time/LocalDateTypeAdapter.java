package lsh.ext.gson.ext.java.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapter;

/**
 * <p>A type adapter for {@link LocalDate}.</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class LocalDateTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<LocalDate> {

	private static final TypeAdapter<LocalDate> instance = new LocalDateTypeAdapter(null);

	private LocalDateTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link LocalDateTypeAdapter} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<LocalDate> getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link LocalDateTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<LocalDate> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalDateTypeAdapter(dateTimeFormatter);
	}

	@Override
	protected LocalDate doFromString(final String string) {
		return LocalDate.parse(string);
	}

	@Override
	protected LocalDate doFromString(final String string, final DateTimeFormatter formatter) {
		return LocalDate.parse(string, formatter);
	}

}
