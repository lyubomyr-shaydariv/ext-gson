package lsh.ext.gson.ext.java.time;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapter;

/**
 * <p>A type adapter for {@link MonthDay}.</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class MonthDayTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<MonthDay> {

	private static final TypeAdapter<MonthDay> defaultInstance = new MonthDayTypeAdapter(null);

	private MonthDayTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link MonthDayTypeAdapter} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<MonthDay> getDefaultInstance() {
		return defaultInstance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link MonthDayTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<MonthDay> get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return defaultInstance;
		}
		return new MonthDayTypeAdapter(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected MonthDay doFromString(@Nonnull final String string) {
		return MonthDay.parse(string);
	}

	@Nonnull
	@Override
	protected MonthDay doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return MonthDay.parse(string, formatter);
	}

}
