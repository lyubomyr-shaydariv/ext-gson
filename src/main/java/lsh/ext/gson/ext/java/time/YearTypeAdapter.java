package lsh.ext.gson.ext.java.time;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapter;

/**
 * <p>A type adapter for {@link Year}.</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class YearTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<Year> {

	private static final TypeAdapter<Year> defaultInstance = new YearTypeAdapter(null);

	private YearTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link YearTypeAdapter} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<Year> getDefaultInstance() {
		return defaultInstance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link YearTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<Year> get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return defaultInstance;
		}
		return new YearTypeAdapter(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected Year doFromString(@Nonnull final String string) {
		return Year.parse(string);
	}

	@Nonnull
	@Override
	protected Year doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return Year.parse(string, formatter);
	}

}
