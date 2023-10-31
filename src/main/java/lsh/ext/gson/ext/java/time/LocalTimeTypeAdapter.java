package lsh.ext.gson.ext.java.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ITypeAdapterFactory;

public final class LocalTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<LocalTime> {

	@Getter(onMethod_ = @SuppressFBWarnings("MS_EXPOSE_REP"))
	private static final TypeAdapter<LocalTime> instance = getInstance(DateTimeFormatter.ISO_LOCAL_TIME);

	private LocalTimeTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter, LocalTime::from);
	}

	public static TypeAdapter<LocalTime> getInstance(final DateTimeFormatter dateTimeFormatter) {
		return new LocalTimeTypeAdapter(dateTimeFormatter)
				.nullSafe();
	}

	public static final class Factory
			extends AbstractBaseTypeAdapterFactory<LocalTime> {

		@Getter(onMethod_ = @SuppressFBWarnings("MS_EXPOSE_REP"))
		private static final ITypeAdapterFactory<LocalTime> instance = new Factory(LocalTimeTypeAdapter.getInstance());

		private Factory(final TypeAdapter<LocalTime> typeAdapter) {
			super(LocalTime.class, typeAdapter);
		}

		public static ITypeAdapterFactory<LocalTime> getInstance(@Nullable final DateTimeFormatter dateTimeFormatter) {
			if ( dateTimeFormatter == null ) {
				return instance;
			}
			return new Factory(LocalTimeTypeAdapter.getInstance(dateTimeFormatter));
		}

	}

}
