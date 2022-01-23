package lsh.ext.gson.ext.java.time;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * <p>Implements a type adapter factory for {@link Year}</p>
 *
 * @author Lyubomyr Shaydariv
 */
public final class YearTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<Year> {

	private static final TypeAdapterFactory instance = new YearTypeAdapterFactory(null);

	private YearTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(Year.class, dateTimeFormatter);
	}

	/**
	 * @return An instance of {@link YearTypeAdapterFactory} with the Java-default {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance() {
		return instance;
	}

	/**
	 * @param dateTimeFormatter Date/time formatter instance
	 *
	 * @return An instance of {@link YearTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new YearTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<Year> create() {
		return YearTypeAdapter.getInstance();
	}

	@Override
	protected TypeAdapter<Year> create(final DateTimeFormatter dateTimeFormatter) {
		return YearTypeAdapter.getInstance(dateTimeFormatter);
	}

}
