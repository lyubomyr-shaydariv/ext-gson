package lsh.ext.gson.ext.java.time;

import java.time.Period;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.AbstractCharSequenceTypeAdapter;
import lsh.ext.gson.AbstractClassTypeAdapterFactory;
import lsh.ext.gson.ITypeAdapterFactory;

public final class PeriodTypeAdapter
		extends AbstractCharSequenceTypeAdapter<Period> {

	@Getter
	@SuppressFBWarnings("MS_EXPOSE_REP")
	private static final TypeAdapter<Period> instance = new PeriodTypeAdapter()
			.nullSafe();

	@Override
	protected Period fromCharSequence(final CharSequence cs) {
		return Period.parse(cs);
	}

	@Override
	protected CharSequence toCharSequence(final Period value) {
		return value.toString();
	}

	public static final class Factory
			extends AbstractClassTypeAdapterFactory<Period> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<Period> instance = new Factory(PeriodTypeAdapter.instance);

		private final TypeAdapter<Period> typeAdapter;

		private Factory(final TypeAdapter<Period> typeAdapter) {
			super(Period.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<Period> getInstance(final TypeAdapter<Period> typeAdapter) {
			return new Factory(typeAdapter);
		}

		@Override
		protected TypeAdapter<Period> createTypeAdapter(final Gson gson, final TypeToken<Period> typeToken) {
			return typeAdapter;
		}

	}

}
