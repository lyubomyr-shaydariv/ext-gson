package lsh.ext.gson.ext.java.time;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link YearMonth}.
 *
 * @author Lyubomyr Shaydariv
 */
public final class YearMonthTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<YearMonth> {

	private static final TypeAdapterFactory instance = new YearMonthTypeAdapterFactory(null);

	private YearMonthTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(YearMonth.class, dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link YearMonthTypeAdapterFactory} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link YearMonthTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new YearMonthTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<YearMonth> create() {
		return YearMonthTypeAdapter.getInstance();
	}

	@Override
	protected TypeAdapter<YearMonth> create(final DateTimeFormatter dateTimeFormatter) {
		return YearMonthTypeAdapter.getInstance(dateTimeFormatter);
	}

}
