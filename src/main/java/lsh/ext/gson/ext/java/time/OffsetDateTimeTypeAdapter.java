package lsh.ext.gson.ext.java.time;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.AbstractClassTypeAdapterFactory;
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
			extends AbstractClassTypeAdapterFactory<OffsetDateTime> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<OffsetDateTime> instance = new Factory(OffsetDateTimeTypeAdapter.instance);

		private final TypeAdapter<OffsetDateTime> typeAdapter;

		private Factory(final TypeAdapter<OffsetDateTime> typeAdapter) {
			super(OffsetDateTime.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<OffsetDateTime> getInstance(final TypeAdapter<OffsetDateTime> typeAdapter) {
			return new Factory(typeAdapter);
		}

		@Override
		protected TypeAdapter<OffsetDateTime> createTypeAdapter(final Gson gson, final TypeToken<? super OffsetDateTime> typeToken) {
			return typeAdapter;
		}

	}

}
