package lsh.ext.gson.ext.java.time;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link OffsetDateTime}</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class OffsetDateTimeTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<OffsetDateTime> {

	private static final TypeAdapterFactory instance = new OffsetDateTimeTypeAdapterFactory(null);

	private OffsetDateTimeTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(OffsetDateTime.class, dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link OffsetDateTimeTypeAdapterFactory} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link OffsetDateTimeTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new OffsetDateTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<OffsetDateTime> create() {
		return OffsetDateTimeTypeAdapter.getInstance();
	}

	@Override
	protected TypeAdapter<OffsetDateTime> create(final DateTimeFormatter dateTimeFormatter) {
		return OffsetDateTimeTypeAdapter.create(dateTimeFormatter);
	}

}
