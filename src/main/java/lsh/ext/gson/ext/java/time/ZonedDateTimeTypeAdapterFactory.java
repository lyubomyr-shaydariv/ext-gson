package lsh.ext.gson.ext.java.time;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link ZonedDateTime}</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class ZonedDateTimeTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<ZonedDateTime> {

	private static final TypeAdapterFactory instance = new ZonedDateTimeTypeAdapterFactory(null);

	private ZonedDateTimeTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(ZonedDateTime.class, dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link ZonedDateTimeTypeAdapterFactory} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link ZonedDateTimeTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new ZonedDateTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<ZonedDateTime> create() {
		return ZonedDateTimeTypeAdapter.getInstance();
	}

	@Override
	protected TypeAdapter<ZonedDateTime> create(final DateTimeFormatter dateTimeFormatter) {
		return ZonedDateTimeTypeAdapter.create(dateTimeFormatter);
	}

}
