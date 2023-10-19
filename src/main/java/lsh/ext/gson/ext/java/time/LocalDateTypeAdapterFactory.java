package lsh.ext.gson.ext.java.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;

/**
 * Implements a type adapter factory for {@link LocalDate}.
 */
public final class LocalDateTypeAdapterFactory
		extends AbstractBaseTypeAdapterFactory<LocalDate> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapterFactory instance = new LocalDateTypeAdapterFactory(Adapter.getInstance());

	private LocalDateTypeAdapterFactory(final TypeAdapter<LocalDate> typeAdapter) {
		super(LocalDate.class, typeAdapter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link LocalDateTypeAdapterFactory}.
	 */
	public static TypeAdapterFactory getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalDateTypeAdapterFactory(Adapter.getInstance(dateTimeFormatter));
	}

	/**
	 * A formatted type adapter for {@link LocalDate}.
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapter<LocalDate> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<LocalDate> instance = getInstance(DateTimeFormatter.ISO_LOCAL_DATE);

		private Adapter(final DateTimeFormatter dateTimeFormatter) {
			super(dateTimeFormatter, LocalDate::from);
		}

		public static TypeAdapter<LocalDate> getInstance(final DateTimeFormatter dateTimeFormatter) {
			return new Adapter(dateTimeFormatter)
					.nullSafe();
		}

	}

}
