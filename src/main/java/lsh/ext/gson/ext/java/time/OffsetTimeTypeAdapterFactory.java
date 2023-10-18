package lsh.ext.gson.ext.java.time;

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ext.java.time.temporal.AbstractTemporalAccessorTypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link OffsetTime}.
 */
public final class OffsetTimeTypeAdapterFactory
		extends AbstractTemporalAccessorTypeAdapterFactory<OffsetTime> {

	@Getter
	private static final TypeAdapterFactory instance = new OffsetTimeTypeAdapterFactory(null);

	private OffsetTimeTypeAdapterFactory(@Nullable final DateTimeFormatter dateTimeFormatter) {
		super(OffsetTime.class, dateTimeFormatter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link OffsetTimeTypeAdapterFactory} with a custom {@link DateTimeFormatter}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new OffsetTimeTypeAdapterFactory(dateTimeFormatter);
	}

	@Override
	protected TypeAdapter<OffsetTime> create() {
		return OffsetTimeTypeAdapterFactory.Adapter.getInstance();
	}

	@Override
	protected TypeAdapter<OffsetTime> create(final DateTimeFormatter dateTimeFormatter) {
		return OffsetTimeTypeAdapterFactory.Adapter.getInstance(dateTimeFormatter);
	}

	/**
	 * A type adapter for {@link OffsetTime}.
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapterFactory.Adapter<OffsetTime> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<OffsetTime> instance = new Adapter(null)
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
		public static TypeAdapter<OffsetTime> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
			if ( dateTimeFormatter == null ) {
				return instance;
			}
			return new Adapter(dateTimeFormatter);
		}

		@Override
		protected OffsetTime doFromString(final String text) {
			return OffsetTime.parse(text);
		}

		@Override
		protected OffsetTime doFromString(final String text, final DateTimeFormatter formatter) {
			return OffsetTime.parse(text, formatter);
		}

	}

}
