package lsh.ext.gson.adapters.java8.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;

/**
 * <p>A type adapter for {@link LocalTime}.</p>
 *
 * @author Lyubomyr Shaydariv
 * @see 0-SNAPSHOT
 */
public final class LocalTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<LocalTime> {

	private static final TypeAdapter<LocalTime> instance = new LocalTimeTypeAdapter(null);

	private LocalTimeTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link LocalTimeTypeAdapter} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<LocalTime> get() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link LocalTimeTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<LocalTime> get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalTimeTypeAdapter(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected LocalTime doFromString(@Nonnull final String string) {
		return LocalTime.parse(string);
	}

	@Nonnull
	@Override
	protected LocalTime doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return LocalTime.parse(string, formatter);
	}

}
