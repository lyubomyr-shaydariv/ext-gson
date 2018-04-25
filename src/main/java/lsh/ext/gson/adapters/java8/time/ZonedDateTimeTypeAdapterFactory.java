package lsh.ext.gson.adapters.java8.time;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link ZonedDateTime}</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
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
	public static TypeAdapterFactory get() {
		return instance;
	}

	/**
	 * @return An instance of {@link ZonedDateTimeTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new ZonedDateTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected TypeAdapter<ZonedDateTime> create() {
		return ZonedDateTimeTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected TypeAdapter<ZonedDateTime> create(@Nonnull final DateTimeFormatter dateTimeFormatter) {
		return ZonedDateTimeTypeAdapter.get(dateTimeFormatter);
	}

}
