package lsh.ext.gson.ext.java.time;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapter;

/**
 * <p>A type adapter for {@link YearMonth}.</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class YearMonthTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<YearMonth> {

	private static final TypeAdapter<YearMonth> defaultInstance = new YearMonthTypeAdapter(null);

	private YearMonthTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link YearMonthTypeAdapter} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<YearMonth> getDefaultInstance() {
		return defaultInstance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link YearMonthTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<YearMonth> create(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return defaultInstance;
		}
		return new YearMonthTypeAdapter(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected YearMonth doFromString(@Nonnull final String string) {
		return YearMonth.parse(string);
	}

	@Nonnull
	@Override
	protected YearMonth doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return YearMonth.parse(string, formatter);
	}

}
