package lsh.ext.gson.ext.java.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link LocalDate}.
 */
public final class LocalDateTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<LocalDate> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapterFactory instance = new LocalDateTypeAdapterFactory(null);

	private LocalDateTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(LocalDate.class, dateTimeFormatter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter instance
	 *
	 * @return An instance of {@link LocalDateTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalDateTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<LocalDate> create() {
		return LocalDateTypeAdapterFactory.Adapter.getInstance();
	}

	@Override
	protected TypeAdapter<LocalDate> create(final DateTimeFormatter dateTimeFormatter) {
		return LocalDateTypeAdapterFactory.Adapter.getInstance(dateTimeFormatter);
	}

	/**
	 * A type adapter for {@link LocalDate}.
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapterFactory.Adapter<LocalDate> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<LocalDate> instance = new Adapter(null)
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
		public static TypeAdapter<LocalDate> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
			if ( dateTimeFormatter == null ) {
				return instance;
			}
			return new Adapter(dateTimeFormatter);
		}

		@Override
		protected LocalDate doFromString(final String text) {
			return LocalDate.parse(text);
		}

		@Override
		protected LocalDate doFromString(final String text, final DateTimeFormatter formatter) {
			return LocalDate.parse(text, formatter);
		}

	}

}
