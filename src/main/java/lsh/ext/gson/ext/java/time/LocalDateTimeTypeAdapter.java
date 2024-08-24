package lsh.ext.gson.ext.java.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lsh.ext.gson.ITypeAdapterFactory;

public final class LocalDateTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<LocalDateTime> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<LocalDateTime> instance = getInstance(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

	private LocalDateTimeTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter, LocalDateTime::from);
	}

	public static TypeAdapter<LocalDateTime> getInstance(final DateTimeFormatter dateTimeFormatter) {
		return new LocalDateTimeTypeAdapter(dateTimeFormatter)
				.nullSafe();
	}

	public static final class Factory
			extends AbstractBaseTypeAdapterFactory<LocalDateTime> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<LocalDateTime> instance = new Factory(LocalDateTimeTypeAdapter.instance);

		@Getter(AccessLevel.PROTECTED)
		private final TypeAdapter<LocalDateTime> typeAdapter;

		private Factory(final TypeAdapter<LocalDateTime> typeAdapter) {
			super(LocalDateTime.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<LocalDateTime> getInstance(final TypeAdapter<LocalDateTime> typeAdapter) {
			return new Factory(typeAdapter);
		}

	}

}
