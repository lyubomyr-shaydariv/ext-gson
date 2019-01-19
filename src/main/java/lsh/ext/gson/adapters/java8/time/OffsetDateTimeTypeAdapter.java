package lsh.ext.gson.adapters.java8.time;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;

/**
 * <p>A type adapter for {@link OffsetDateTime}.</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class OffsetDateTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<OffsetDateTime> {

	private static final TypeAdapter<OffsetDateTime> instance = new OffsetDateTimeTypeAdapter(null);

	private OffsetDateTimeTypeAdapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link OffsetDateTimeTypeAdapter} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<OffsetDateTime> get() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link OffsetDateTimeTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapter<OffsetDateTime> get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new OffsetDateTimeTypeAdapter(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected OffsetDateTime doFromString(@Nonnull final String string) {
		return OffsetDateTime.parse(string);
	}

	@Nonnull
	@Override
	protected OffsetDateTime doFromString(@Nonnull final String string, @Nonnull final DateTimeFormatter formatter) {
		return OffsetDateTime.parse(string, formatter);
	}

}
