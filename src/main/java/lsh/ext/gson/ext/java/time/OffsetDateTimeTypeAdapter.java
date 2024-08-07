package lsh.ext.gson.ext.java.time;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.ITypeAdapterFactory;

public final class OffsetDateTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<OffsetDateTime> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<OffsetDateTime> instance = getInstance(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

	private OffsetDateTimeTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter, OffsetDateTime::from);
	}

	public static TypeAdapter<OffsetDateTime> getInstance(final DateTimeFormatter dateTimeFormatter) {
		return new OffsetDateTimeTypeAdapter(dateTimeFormatter)
				.nullSafe();
	}

	public static final class Factory
			extends AbstractBaseTypeAdapterFactory<OffsetDateTime> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<OffsetDateTime> instance = new Factory(OffsetDateTimeTypeAdapter.getInstance());

		private Factory(final TypeAdapter<OffsetDateTime> typeAdapter) {
			super(OffsetDateTime.class, typeAdapter);
		}

		public static ITypeAdapterFactory<OffsetDateTime> getInstance(final DateTimeFormatter dateTimeFormatter) {
			return new Factory(OffsetDateTimeTypeAdapter.getInstance(dateTimeFormatter));
		}

	}

}
