package lsh.ext.gson.ext.java.time;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.AbstractRawClassTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

public final class YearTypeAdapter
		extends AbstractTemporalAccessorTypeAdapter<Year> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	@SuppressWarnings("checkstyle:magicnumber")
	private static final TypeAdapter<Year> instance = getInstance(new DateTimeFormatterBuilder()
			.parseLenient()
			.appendValue(ChronoField.YEAR, 1, 10, SignStyle.NORMAL)
			.toFormatter()
	);

	private YearTypeAdapter(final DateTimeFormatter dateTimeFormatter) {
		super(dateTimeFormatter, Year::from);
	}

	public static TypeAdapter<Year> getInstance(final DateTimeFormatter dateTimeFormatter) {
		return new YearTypeAdapter(dateTimeFormatter)
				.nullSafe();
	}

	public static final class Factory
			extends AbstractRawClassTypeAdapterFactory<Year> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<Year> instance = new Factory(YearTypeAdapter.instance);

		private final TypeAdapter<Year> typeAdapter;

		private Factory(final TypeAdapter<Year> typeAdapter) {
			super(Year.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<Year> getInstance(final TypeAdapter<Year> typeAdapter) {
			return new Factory(typeAdapter);
		}

		@Override
		protected TypeAdapter<Year> createTypeAdapter(final Gson gson, final TypeToken<? super Year> typeToken) {
			return typeAdapter;
		}

	}

}
