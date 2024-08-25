package lsh.ext.gson.ext.java.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.AbstractClassTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

public final class LocalDateTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<LocalDate> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<LocalDate> instance = getInstance(DateTimeFormatter.ISO_LOCAL_DATE);

	private LocalDateTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter, LocalDate::from);
	}

	public static TypeAdapter<LocalDate> getInstance(final DateTimeFormatter dateTimeFormatter) {
		return new LocalDateTypeAdapter(dateTimeFormatter)
				.nullSafe();
	}

	public static final class Factory
			extends AbstractClassTypeAdapterFactory<LocalDate> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<LocalDate> instance = new Factory(LocalDateTypeAdapter.instance);

		private final TypeAdapter<LocalDate> typeAdapter;

		private Factory(final TypeAdapter<LocalDate> typeAdapter) {
			super(LocalDate.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<LocalDate> getInstance(final TypeAdapter<LocalDate> typeAdapter) {
			return new Factory(typeAdapter);
		}

		@Override
		protected TypeAdapter<LocalDate> createTypeAdapter(final Gson gson, final TypeToken<? super LocalDate> typeToken) {
			return typeAdapter;
		}

	}

}
