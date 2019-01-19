package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;

/**
 * <p>A type adapter for {@link LocalDate}.</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class LocalDateTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<LocalDate> {

	private static final TypeAdapter<LocalDate> instance = new LocalDateTypeAdapter(null);

	private LocalDateTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link LocalDateTypeAdapter} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<LocalDate> get() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link LocalDateTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<LocalDate> get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalDateTypeAdapter(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected LocalDate doFromString(@Nonnull final String string) {
		return LocalDate.parse(string);
	}

	@Nonnull
	@Override
	protected LocalDate doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return LocalDate.parse(string, formatter);
	}

}
