package lsh.ext.gson.ext.java.time;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link Year}.
 *
 * @author Lyubomyr Shaydariv
 */
public final class YearTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<Year> {

	@Getter
	private static final TypeAdapterFactory instance = new YearTypeAdapterFactory(null);

	private YearTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(Year.class, dateTimeFormatter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter instance
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
		return YearTypeAdapterFactory.Adapter.getInstance();
	}

	@Override
	protected TypeAdapter<Year> create(final DateTimeFormatter dateTimeFormatter) {
		return YearTypeAdapterFactory.Adapter.getInstance(dateTimeFormatter);
	}

	/**
	 * A type adapter for {@link Year}.
	 *
	 * @author Lyubomyr Shaydariv
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapterFactory.Adapter<Year> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<Year> instance = new Adapter(null)
				.nullSafe();

		private Adapter(@Nullable final DateTimeFormatter dateTimeFormatter) {
			super(dateTimeFormatter);
		}

		/**
		 * @param dateTimeFormatter
		 * 		Date/time formatter
		 *
		 * @return An instance of {@link Adapter} with a custom {@link DateTimeFormatter}.
		 */
		@SuppressFBWarnings("MS_EXPOSE_REP")
		public static TypeAdapter<Year> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
			if ( dateTimeFormatter == null ) {
				return instance;
			}
			return new Adapter(dateTimeFormatter);
		}

		@Override
		protected Year doFromString(final String text) {
			return Year.parse(text);
		}

		@Override
		protected Year doFromString(final String text, final DateTimeFormatter formatter) {
			return Year.parse(text, formatter);
		}

	}

}
