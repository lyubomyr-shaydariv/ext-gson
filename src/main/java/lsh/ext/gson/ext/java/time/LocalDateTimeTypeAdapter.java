package lsh.ext.gson.ext.java.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapter;

/**
 * A type adapter for {@link LocalDateTime}.
 *
 * @author Lyubomyr Shaydariv
 */
public final class LocalDateTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<LocalDateTime> {

	private static final TypeAdapter<LocalDateTime> instance = new LocalDateTimeTypeAdapter(null);

	private LocalDateTimeTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link LocalDateTimeTypeAdapter} with the Java-default {@link DateTimeFormatter}.
	 */
	@SuppressFBWarnings("MS_EXPOSE_REP")
	public static TypeAdapter<LocalDateTime> getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link LocalDateTimeTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	@SuppressFBWarnings("MS_EXPOSE_REP")
	public static TypeAdapter<LocalDateTime> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalDateTimeTypeAdapter(dateTimeFormatter);
	}

	@Override
	protected LocalDateTime doFromString(final String string) {
		return LocalDateTime.parse(string);
	}

	@Override
	protected LocalDateTime doFromString(final String string, final DateTimeFormatter formatter) {
		return LocalDateTime.parse(string, formatter);
	}

}
