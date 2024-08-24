package lsh.ext.gson.ext.java.time;

import java.time.DayOfWeek;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
			extends AbstractBaseTypeAdapterFactory<DayOfWeek> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<DayOfWeek> instance = new Factory(DayOfWeekTypeAdapter.instance);

		@Getter(AccessLevel.PROTECTED)
		private final TypeAdapter<DayOfWeek> typeAdapter;

		private Factory(final TypeAdapter<DayOfWeek> typeAdapter) {
			super(DayOfWeek.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<DayOfWeek> getInstance(final TypeAdapter<DayOfWeek> typeAdapter) {
			return new Factory(typeAdapter);
		}

	}

}
