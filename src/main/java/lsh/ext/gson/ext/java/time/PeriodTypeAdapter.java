package lsh.ext.gson.ext.java.time;

import java.time.Period;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.Getter;
import lsh.ext.gson.AbstractCharSequenceTypeAdapter;
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
			extends AbstractBaseTypeAdapterFactory<Period> {

		@Getter
		@SuppressFBWarnings("MS_EXPOSE_REP")
		private static final ITypeAdapterFactory<Period> instance = new Factory(PeriodTypeAdapter.instance);

		@Getter(AccessLevel.PROTECTED)
		private final TypeAdapter<Period> typeAdapter;

		private Factory(final TypeAdapter<Period> typeAdapter) {
			super(Period.class);
			this.typeAdapter = typeAdapter;
		}

		public static ITypeAdapterFactory<Period> getInstance(final TypeAdapter<Period> typeAdapter) {
			return new Factory(typeAdapter);
		}

	}

}
