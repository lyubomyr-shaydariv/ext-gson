package lsh.ext.gson.ext.java.time;

import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ITypeAdapterFactory;

public final class OffsetTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<OffsetTime> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapter<OffsetTime> instance = getInstance(DateTimeFormatter.ISO_OFFSET_TIME);

	private OffsetTimeTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter, OffsetTime::from);
	}

	public static TypeAdapter<OffsetTime> getInstance(final DateTimeFormatter dateTimeFormatter) {
		return new OffsetTimeTypeAdapter(dateTimeFormatter)
				.nullSafe();
	}

	public static final class Factory
			extends AbstractBaseTypeAdapterFactory<OffsetTime> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final ITypeAdapterFactory<OffsetTime> instance = new Factory(OffsetTimeTypeAdapter.getInstance());

		private Factory(final TypeAdapter<OffsetTime> typeAdapter) {
			super(OffsetTime.class, typeAdapter);
		}

		public static ITypeAdapterFactory<OffsetTime> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
			if ( dateTimeFormatter == null ) {
				return instance;
			}
			return new Factory(OffsetTimeTypeAdapter.getInstance(dateTimeFormatter));
		}

	}

}
