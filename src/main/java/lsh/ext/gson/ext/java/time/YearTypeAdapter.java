package lsh.ext.gson.ext.java.time;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapter;

/**
 * A type adapter for {@link Year}.
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
	@SuppressFBWarnings("MS_EXPOSE_REP")
	public static TypeAdapter<Year> getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link YearTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	@SuppressFBWarnings("MS_EXPOSE_REP")
	public static TypeAdapter<Year> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new YearTypeAdapter(dateTimeFormatter);
	}

	@Override
	protected Year doFromString(final String text) {
		return Year.parse(text);
	}

	@Override
	protected Year doFromString(final String text, final DateTimeFormatter formatter) {
		return Year.parse(text, formatter);
	}

}
