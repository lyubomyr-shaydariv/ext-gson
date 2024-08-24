package lsh.ext.gson.ext.java.time;

import java.time.Month;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lsh.ext.gson.AbstractStringTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;

public final class MonthTypeAdapter
		extends AbstractStringTypeAdapter<Month> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<Month> instance = new MonthTypeAdapter()
			.nullSafe();

	@Override
	protected Month fromString(final String text) {
		return Month.valueOf(text);
	}

	@Override
	protected String toString(final Month value) {
		return value.toString();
	}

	public static final class Factory
			extends AbstractBaseTypeAdapterFactory<Month> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<Month> instance = new Factory(MonthTypeAdapter.instance);

		@Getter(AccessLevel.PROTECTED)
		private final TypeAdapter<Month> typeAdapter;

		private Factory(final TypeAdapter<Month> typeAdapter) {
			super(Month.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<Month> getInstance(final TypeAdapter<Month> typeAdapter) {
			return new Factory(typeAdapter);
		}

	}

}
