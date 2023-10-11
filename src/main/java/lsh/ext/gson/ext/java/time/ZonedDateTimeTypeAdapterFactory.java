package lsh.ext.gson.ext.java.time;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ITypeAdapterFactory;

public final class ZonedDateTimeTypeAdapterFactory
		extends AbstractBaseTypeAdapterFactory<ZonedDateTime>
		implements ITypeAdapterFactory<ZonedDateTime> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final ITypeAdapterFactory<ZonedDateTime> instance = new ZonedDateTimeTypeAdapterFactory(Adapter.getInstance());

	private ZonedDateTimeTypeAdapterFactory(final TypeAdapter<ZonedDateTime> typeAdapter) {
		super(ZonedDateTime.class, typeAdapter);
	}

	public static ITypeAdapterFactory<ZonedDateTime> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
		if ( dateTimeFormatter == null ) {
			return instance;
		}
		return new ZonedDateTimeTypeAdapterFactory(Adapter.getInstance(dateTimeFormatter));
	}

	public static final class Adapter
			extends AbstractTemporalAccessorTypeAdapter<ZonedDateTime> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<ZonedDateTime> instance = getInstance(DateTimeFormatter.ISO_ZONED_DATE_TIME);

		private Adapter(final DateTimeFormatter dateTimeFormatter) {
			super(dateTimeFormatter, ZonedDateTime::from);
		}

		public static TypeAdapter<ZonedDateTime> getInstance(final DateTimeFormatter dateTimeFormatter) {
			return new Adapter(dateTimeFormatter)
					.nullSafe();
		}

	}

}
