package lsh.ext.gson.ext.java.time;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapter;

/**
 * <p>A type adapter for {@link Year}.</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class YearTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<Year> {

	private static final TypeAdapter<Year> instance = new YearTypeAdapter(null);

	private YearTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link YearTypeAdapter} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<Year> getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link YearTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<Year> create(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new YearTypeAdapter(dateTimeFormatter);
	}

	@Override
	protected Year doFromString(final String string) {
		return Year.parse(string);
	}

	@Override
	protected Year doFromString(final String string, final DateTimeFormatter formatter) {
		return Year.parse(string, formatter);
	}

}
