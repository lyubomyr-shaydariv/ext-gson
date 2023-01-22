package lsh.ext.gson.ext.java.time;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lombok.Getter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link MonthDay}.
 *
 * @author Lyubomyr Shaydariv
 */
public final class MonthDayTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<MonthDay> {

	@Getter
	private static final TypeAdapterFactory instance = new MonthDayTypeAdapterFactory(null);

	private MonthDayTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(MonthDay.class, dateTimeFormatter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link MonthDayTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new MonthDayTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<MonthDay> create() {
		return MonthDayTypeAdapter.getInstance();
	}

	@Override
	protected TypeAdapter<MonthDay> create(final DateTimeFormatter dateTimeFormatter) {
		return MonthDayTypeAdapter.getInstance(dateTimeFormatter);
	}

}
