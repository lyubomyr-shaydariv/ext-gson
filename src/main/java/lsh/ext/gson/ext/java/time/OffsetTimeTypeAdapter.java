package lsh.ext.gson.ext.java.time;

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapter;

/**
 * A type adapter for {@link OffsetTime}.
 *
 * @author Lyubomyr Shaydariv
 */
public final class OffsetTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<OffsetTime> {

	private static final TypeAdapter<OffsetTime> instance = new OffsetTimeTypeAdapter(null);

	private OffsetTimeTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link OffsetTimeTypeAdapter} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<OffsetTime> getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link OffsetTimeTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<OffsetTime> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new OffsetTimeTypeAdapter(dateTimeFormatter);
	}

	@Override
	protected OffsetTime doFromString(final String string) {
		return OffsetTime.parse(string);
	}

	@Override
	protected OffsetTime doFromString(final String string, final DateTimeFormatter formatter) {
		return OffsetTime.parse(string, formatter);
	}

}
