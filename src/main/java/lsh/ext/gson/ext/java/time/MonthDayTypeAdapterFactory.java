package lsh.ext.gson.ext.java.time;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link MonthDay}</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class MonthDayTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<MonthDay> {

	private static final TypeAdapterFactory defaultInstance = new MonthDayTypeAdapterFactory(null);

	private MonthDayTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(MonthDay.class, dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link MonthDayTypeAdapterFactory} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getDefaultInstance() {
		return defaultInstance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link MonthDayTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return defaultInstance;
		}
		return new MonthDayTypeAdapterFactory(dateTimeFormatter);
	}

	@Nonnull
	@Override
	protected TypeAdapter<MonthDay> create() {
		return MonthDayTypeAdapter.getDefaultInstance();
	}

	@Nonnull
	@Override
	protected TypeAdapter<MonthDay> create(@Nonnull final DateTimeFormatter dateTimeFormatter) {
		return MonthDayTypeAdapter.create(dateTimeFormatter);
	}

}
