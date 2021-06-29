package lsh.ext.gson.ext.java.time;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapter;

/**
 * <p>A type adapter for {@link ZonedDateTime}.</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
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

	@Nonnull
	@Override
	protected ZonedDateTime doFromString(@Nonnull final String string) {
		return ZonedDateTime.parse(string);
	}

	@Nonnull
	@Override
	protected ZonedDateTime doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return ZonedDateTime.parse(string, formatter);
	}

}
