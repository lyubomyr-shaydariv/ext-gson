package lsh.ext.gson.ext.java.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.AbstractClassTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

public final class LocalTimeTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<LocalTime> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<LocalTime> instance = getInstance(DateTimeFormatter.ISO_LOCAL_TIME);

	private LocalTimeTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter, LocalTime::from);
	}

	public static TypeAdapter<LocalTime> getInstance(final DateTimeFormatter dateTimeFormatter) {
		return new LocalTimeTypeAdapter(dateTimeFormatter)
				.nullSafe();
	}

	public static final class Factory
			extends AbstractClassTypeAdapterFactory<LocalTime> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<LocalTime> instance = new Factory(LocalTimeTypeAdapter.instance);

		private final TypeAdapter<LocalTime> typeAdapter;

		private Factory(final TypeAdapter<LocalTime> typeAdapter) {
			super(LocalTime.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<LocalTime> getInstance(final TypeAdapter<LocalTime> typeAdapter) {
			return new Factory(typeAdapter);
		}

		@Override
		protected TypeAdapter<LocalTime> createTypeAdapter(final Gson gson, final TypeToken<LocalTime> typeToken) {
			return typeAdapter;
		}

	}

}
