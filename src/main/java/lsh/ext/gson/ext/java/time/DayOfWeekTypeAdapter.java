package lsh.ext.gson.ext.java.time;

import java.time.DayOfWeek;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.AbstractRawClassTypeAdapterFactory;
import lsh.ext.gson.AbstractStringTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class DayOfWeekTypeAdapter
		extends AbstractStringTypeAdapter<DayOfWeek> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<DayOfWeek> instance = new DayOfWeekTypeAdapter()
			.nullSafe();

	@Override
	protected DayOfWeek fromString(final String text) {
		return DayOfWeek.valueOf(text);
	}

	@Override
	protected String toString(final DayOfWeek value) {
		return value.toString();
	}

	public static final class Factory
			extends AbstractRawClassTypeAdapterFactory<DayOfWeek> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<DayOfWeek> instance = new Factory(DayOfWeekTypeAdapter.instance);

		private final TypeAdapter<DayOfWeek> typeAdapter;

		private Factory(final TypeAdapter<DayOfWeek> typeAdapter) {
			super(DayOfWeek.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<DayOfWeek> getInstance(final TypeAdapter<DayOfWeek> typeAdapter) {
			return new Factory(typeAdapter);
		}

		@Override
		protected TypeAdapter<DayOfWeek> createTypeAdapter(final Gson gson, final TypeToken<? super DayOfWeek> typeToken) {
			return typeAdapter;
		}

	}

}
