package lsh.ext.gson.ext.java.time;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;

/**
 * Implements a type adapter factory for {@link OffsetDateTime}.
 */
public final class OffsetDateTimeTypeAdapterFactory
		extends AbstractBaseTypeAdapterFactory<OffsetDateTime> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapterFactory instance = new OffsetDateTimeTypeAdapterFactory(Adapter.getInstance());

	private OffsetDateTimeTypeAdapterFactory(final TypeAdapter<OffsetDateTime> typeAdapter) {
		super(OffsetDateTime.class, typeAdapter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link OffsetDateTimeTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new OffsetDateTimeTypeAdapterFactory(Adapter.getInstance(dateTimeFormatter));
	}

	/**
	 * A formatted type adapter for {@link OffsetDateTime}.
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapter<OffsetDateTime> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<OffsetDateTime> instance = getInstance(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

		private Adapter(final DateTimeFormatter dateTimeFormatter) {
			super(dateTimeFormatter, OffsetDateTime::from);
		}

		public static TypeAdapter<OffsetDateTime> getInstance(final DateTimeFormatter dateTimeFormatter) {
			return new Adapter(dateTimeFormatter)
					.nullSafe();
		}

	}

}
