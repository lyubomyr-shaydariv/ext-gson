package lsh.ext.gson.ext.java.time;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapter;

/**
 * <p>A type adapter for {@link OffsetDateTime}.</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class OffsetDateTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<OffsetDateTime> {

	private static final TypeAdapter<OffsetDateTime> instance = new OffsetDateTimeTypeAdapter(null);

	private OffsetDateTimeTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link OffsetDateTimeTypeAdapter} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<OffsetDateTime> getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link OffsetDateTimeTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<OffsetDateTime> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new OffsetDateTimeTypeAdapter(dateTimeFormatter);
	}

	@Override
	protected OffsetDateTime doFromString(final String string) {
		return OffsetDateTime.parse(string);
	}

	@Override
	protected OffsetDateTime doFromString(final String string, final DateTimeFormatter formatter) {
		return OffsetDateTime.parse(string, formatter);
	}

}
