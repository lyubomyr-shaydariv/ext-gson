package lsh.ext.gson.ext.java.time;

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
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

	private static final TypeAdapterFactory defaultInstance = new OffsetTimeTypeAdapterFactory(null);

	private OffsetTimeTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(OffsetTime.class, dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link OffsetTimeTypeAdapterFactory} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getDefaultInstance() {
		return defaultInstance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link OffsetTimeTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return defaultInstance;
		}
		return new OffsetTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected TypeAdapter<OffsetTime> create() {
		return OffsetTimeTypeAdapter.getDefaultInstance();
	}

	@Nonnull
	@Override
	protected TypeAdapter<OffsetTime> create(@Nonnull final DateTimeFormatter dateTimeFormatter) {
		return OffsetTimeTypeAdapter.create(dateTimeFormatter);
	}

}
