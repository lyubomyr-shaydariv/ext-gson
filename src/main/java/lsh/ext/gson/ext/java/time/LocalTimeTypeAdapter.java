package lsh.ext.gson.ext.java.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapter;

/**
 * <p>A type adapter for {@link LocalTime}.</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class LocalTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<LocalTime> {

	private static final TypeAdapter<LocalTime> defaultInstance = new LocalTimeTypeAdapter(null);

	private LocalTimeTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link LocalTimeTypeAdapter} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<LocalTime> getDefaultInstance() {
		return defaultInstance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link LocalTimeTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<LocalTime> create(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return defaultInstance;
		}
		return new LocalTimeTypeAdapter(dateTimeFormatter);
	}

	@Override
	protected LocalTime doFromString(final String string) {
		return LocalTime.parse(string);
	}

	@Override
	protected LocalTime doFromString(final String string, final DateTimeFormatter formatter) {
		return LocalTime.parse(string, formatter);
	}

}
