package lsh.ext.gson.ext.java.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ITypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link LocalTime}.
 */
public final class LocalTimeTypeAdapterFactory
		extends AbstractBaseTypeAdapterFactory<LocalTime>
		implements ITypeAdapterFactory<LocalTime> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final ITypeAdapterFactory<LocalTime> instance = new LocalTimeTypeAdapterFactory(Adapter.getInstance());

	private LocalTimeTypeAdapterFactory(final TypeAdapter<LocalTime> typeAdapter) {
		super(LocalTime.class, typeAdapter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link LocalTimeTypeAdapterFactory}.
	 */
	public static ITypeAdapterFactory<LocalTime> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new LocalTimeTypeAdapterFactory(Adapter.getInstance(dateTimeFormatter));
	}

	/**
	 * A formatted type adapter for {@link LocalTime}.
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapter<LocalTime> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<LocalTime> instance = getInstance(DateTimeFormatter.ISO_LOCAL_TIME);

		private Adapter(final DateTimeFormatter dateTimeFormatter) {
			super(dateTimeFormatter, LocalTime::from);
		}

		@SuppressWarnings("checkstyle:MissingJavadocMethod")
		public static TypeAdapter<LocalTime> getInstance(final DateTimeFormatter dateTimeFormatter) {
			return new Adapter(dateTimeFormatter)
					.nullSafe();
		}

	}

}
