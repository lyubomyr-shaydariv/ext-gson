package lsh.ext.gson.ext.java.time;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapter;

/**
 * A type adapter for {@link OffsetDateTime}.
 *
 * @author Lyubomyr Shaydariv
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
	@SuppressFBWarnings("MS_EXPOSE_REP")
	public static TypeAdapter<OffsetDateTime> getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link OffsetDateTimeTypeAdapter} with a custom {@link DateTimeFormatter}.
	 */
	@SuppressFBWarnings("MS_EXPOSE_REP")
	public static TypeAdapter<OffsetDateTime> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new OffsetDateTimeTypeAdapter(dateTimeFormatter);
	}

	@Override
	protected OffsetDateTime doFromString(final String text) {
		return OffsetDateTime.parse(text);
	}

	@Override
	protected OffsetDateTime doFromString(final String text, final DateTimeFormatter formatter) {
		return OffsetDateTime.parse(text, formatter);
	}

}
