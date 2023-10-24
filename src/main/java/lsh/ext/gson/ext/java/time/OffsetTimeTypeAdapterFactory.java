package lsh.ext.gson.ext.java.time;

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ITypeAdapterFactory;

/**
 * Implements a type adapter factory for {@link OffsetTime}.
 */
public final class OffsetTimeTypeAdapterFactory
		extends AbstractBaseTypeAdapterFactory<OffsetTime>
		implements ITypeAdapterFactory<OffsetTime> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final ITypeAdapterFactory<OffsetTime> instance = new OffsetTimeTypeAdapterFactory(Adapter.getInstance());

	private OffsetTimeTypeAdapterFactory(final TypeAdapter<OffsetTime> typeAdapter) {
		super(OffsetTime.class, typeAdapter);
	}

	/**
	 * @param dateTimeFormatter
	 * 		Date/time formatter
	 *
	 * @return An instance of {@link OffsetTimeTypeAdapterFactory}.
	 */
	public static ITypeAdapterFactory<OffsetTime> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new OffsetTimeTypeAdapterFactory(Adapter.getInstance(dateTimeFormatter));
	}

	/**
	 * A formatted type adapter for {@link OffsetTime}.
	 */
	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapter<OffsetTime> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<OffsetTime> instance = getInstance(DateTimeFormatter.ISO_OFFSET_TIME);

		private Adapter(final DateTimeFormatter dateTimeFormatter) {
			super(dateTimeFormatter, OffsetTime::from);
		}

		@SuppressWarnings("checkstyle:MissingJavadocMethod")
		public static TypeAdapter<OffsetTime> getInstance(final DateTimeFormatter dateTimeFormatter) {
			return new Adapter(dateTimeFormatter)
					.nullSafe();
		}

	}

}
