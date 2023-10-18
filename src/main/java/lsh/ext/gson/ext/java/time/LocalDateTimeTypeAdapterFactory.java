package lsh.ext.gson.ext.java.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link LocalDateTime}.
 */
public final class LocalDateTimeTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<LocalDateTime> {

	@Getter
	private static final TypeAdapterFactory instance = new LocalDateTimeTypeAdapterFactory(null);

	private LocalDateTimeTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(LocalDateTime.class, dateTimeFormatter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter instance
	 *
	 * @return An instance of {@link LocalDateTimeTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalDateTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<LocalDateTime> create() {
		return LocalDateTimeTypeAdapterFactory.Adapter.getInstance();
	}

	@Override
	protected TypeAdapter<LocalDateTime> create(final DateTimeFormatter dateTimeFormatter) {
		return LocalDateTimeTypeAdapterFactory.Adapter.getInstance(dateTimeFormatter);
	}

	/**
	 * A type adapter for {@link LocalDateTime}.
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapterFactory.Adapter<LocalDateTime> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<LocalDateTime> instance = new Adapter(null)
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
		public static TypeAdapter<LocalDateTime> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
			if ( dateTimeFormatter == null ) {
				return instance;
			}
			return new Adapter(dateTimeFormatter);
		}

		@Override
		protected LocalDateTime doFromString(final String text) {
			return LocalDateTime.parse(text);
		}

		@Override
		protected LocalDateTime doFromString(final String text, final DateTimeFormatter formatter) {
			return LocalDateTime.parse(text, formatter);
		}

	}

}
