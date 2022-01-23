package lsh.ext.gson.ext.java.time;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapter;

/**
 * <p>A type adapter for {@link ZonedDateTime}.</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class ZonedDateTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<ZonedDateTime> {

	private static final TypeAdapter<ZonedDateTime> defaultInstance = new ZonedDateTimeTypeAdapter(null);

	private ZonedDateTimeTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link ZonedDateTimeTypeAdapter} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<ZonedDateTime> getDefaultInstance() {
		return defaultInstance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link ZonedDateTimeTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<ZonedDateTime> create(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return defaultInstance;
		}
		return new ZonedDateTimeTypeAdapter(dateTimeFormatter);
	}

	@Override
	protected ZonedDateTime doFromString(final String string) {
		return ZonedDateTime.parse(string);
	}

	@Override
	protected ZonedDateTime doFromString(final String string, final DateTimeFormatter formatter) {
		return ZonedDateTime.parse(string, formatter);
	}

}
