package lsh.ext.gson.ext.java.time;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.AbstractRawClassTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

public final class ZonedDateTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<ZonedDateTime> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<ZonedDateTime> instance = getInstance(DateTimeFormatter.ISO_ZONED_DATE_TIME);

	private ZonedDateTimeTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter, ZonedDateTime::from);
	}

	public static TypeAdapter<ZonedDateTime> getInstance(final DateTimeFormatter dateTimeFormatter) {
		return new ZonedDateTimeTypeAdapter(dateTimeFormatter)
				.nullSafe();
	}

	public static final class Factory
			extends AbstractRawClassTypeAdapterFactory<ZonedDateTime> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<ZonedDateTime> instance = new Factory(ZonedDateTimeTypeAdapter.instance);

		private final TypeAdapter<ZonedDateTime> typeAdapter;

		private Factory(final TypeAdapter<ZonedDateTime> typeAdapter) {
			super(ZonedDateTime.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<ZonedDateTime> getInstance(final TypeAdapter<ZonedDateTime> typeAdapter) {
			return new Factory(typeAdapter);
		}

		@Override
		protected TypeAdapter<ZonedDateTime> createTypeAdapter(final Gson gson, final TypeToken<? super ZonedDateTime> typeToken) {
			return typeAdapter;
		}

	}

}
