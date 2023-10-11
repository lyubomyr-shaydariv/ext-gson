package lsh.ext.gson.ext.java.time;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link OffsetDateTime}.
 *
 * @author Lyubomyr Shaydariv
 */
public final class OffsetDateTimeTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<OffsetDateTime> {

	@Getter
	private static final TypeAdapterFactory instance = new OffsetDateTimeTypeAdapterFactory(null);

	private OffsetDateTimeTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(OffsetDateTime.class, dateTimeFormatter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link OffsetDateTimeTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new OffsetDateTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<OffsetDateTime> create() {
		return OffsetDateTimeTypeAdapterFactory.Adapter.getInstance();
	}

	@Override
	protected TypeAdapter<OffsetDateTime> create(final DateTimeFormatter dateTimeFormatter) {
		return OffsetDateTimeTypeAdapterFactory.Adapter.getInstance(dateTimeFormatter);
	}

	/**
	 * A type adapter for {@link OffsetDateTime}.
	 *
	 * @author Lyubomyr Shaydariv
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapterFactory.Adapter<OffsetDateTime> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<OffsetDateTime> instance = new Adapter(null)
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
		public static TypeAdapter<OffsetDateTime> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
			if ( dateTimeFormatter == null ) {
				return instance;
			}
			return new Adapter(dateTimeFormatter);
		}

		@Override
		protected OffsetDateTime doFromString(final String text) {
			return OffsetDateTime.parse(text);
		}

		@Override
		protected OffsetDateTime doFromString(final String text, final DateTimeFormatter formatter) {
			return OffsetDateTime.parse(text, formatter);
		}

	}

}
