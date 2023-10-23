package lsh.ext.gson.ext.java.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ITypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link LocalDateTime}.
 */
public final class LocalDateTimeTypeAdapterFactory
		extends AbstractBaseTypeAdapterFactory<LocalDateTime>
		implements ITypeAdapterFactory<LocalDateTime> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final ITypeAdapterFactory<LocalDateTime> instance = new LocalDateTimeTypeAdapterFactory(Adapter.getInstance());

	private LocalDateTimeTypeAdapterFactory(final TypeAdapter<LocalDateTime> typeAdapter) {
		super(LocalDateTime.class, typeAdapter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link LocalDateTimeTypeAdapterFactory}.
	 */
	public static ITypeAdapterFactory<LocalDateTime> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalDateTimeTypeAdapterFactory(Adapter.getInstance(dateTimeFormatter));
	}

	/**
	 * A formatted type adapter for {@link LocalDateTime}.
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapter<LocalDateTime> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<LocalDateTime> instance = getInstance(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

		private Adapter(final DateTimeFormatter dateTimeFormatter) {
			super(dateTimeFormatter, LocalDateTime::from);
		}

		public static TypeAdapter<LocalDateTime> getInstance(final DateTimeFormatter dateTimeFormatter) {
			return new Adapter(dateTimeFormatter)
					.nullSafe();
		}

	}

}
