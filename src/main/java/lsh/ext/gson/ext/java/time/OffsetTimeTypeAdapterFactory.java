package lsh.ext.gson.ext.java.time;

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link OffsetTime}</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class OffsetTimeTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<OffsetTime> {

	private static final TypeAdapterFactory instance = new OffsetTimeTypeAdapterFactory(null);

	private OffsetTimeTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(OffsetTime.class, dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link OffsetTimeTypeAdapterFactory} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link OffsetTimeTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new OffsetTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<OffsetTime> create() {
		return OffsetTimeTypeAdapter.getInstance();
	}

	@Override
	protected TypeAdapter<OffsetTime> create(final DateTimeFormatter dateTimeFormatter) {
		return OffsetTimeTypeAdapter.create(dateTimeFormatter);
	}

}
