package lsh.ext.gson.ext.java.time;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link YearMonth}</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class YearMonthTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<YearMonth> {

	private static final TypeAdapterFactory defaultInstance = new YearMonthTypeAdapterFactory(null);

	private YearMonthTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(YearMonth.class, dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link YearMonthTypeAdapterFactory} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getDefaultInstance() {
		return defaultInstance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter
	 *
	 * @return An instance of {@link YearMonthTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory get(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return defaultInstance;
		}
		return new YearMonthTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<YearMonth> create() {
		return YearMonthTypeAdapter.getDefaultInstance();
	}

	@Override
	protected TypeAdapter<YearMonth> create(final DateTimeFormatter dateTimeFormatter) {
		return YearMonthTypeAdapter.create(dateTimeFormatter);
	}

}
