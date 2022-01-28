package lsh.ext.gson.ext.java.time;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapter;

/**
 * A type adapter for {@link MonthDay}.
 *
 * @author Lyubomyr Shaydariv
 */
public final class MonthDayTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<MonthDay> {

	private static final TypeAdapter<MonthDay> instance = new MonthDayTypeAdapter(null);

	private MonthDayTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link MonthDayTypeAdapter} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<MonthDay> getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link MonthDayTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<MonthDay> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new MonthDayTypeAdapter(dateTimeFormatter);
	}

	@Override
	protected MonthDay doFromString(final String string) {
		return MonthDay.parse(string);
	}

	@Override
	protected MonthDay doFromString(final String string, final DateTimeFormatter formatter) {
		return MonthDay.parse(string, formatter);
	}

}
