package lsh.ext.gson.ext.java.time;

import java.time.Period;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.AbstractStringTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;

public final class PeriodTypeAdapter
		extends AbstractStringTypeAdapter<Period> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final TypeAdapter<Period> instance = new PeriodTypeAdapter()
			.nullSafe();

	@Override
	protected Period fromString(final String text) {
		return Period.parse(text);
	}

	@Override
	protected String toString(final Period value) {
		return value.toString();
	}

	public static final class Factory
			extends AbstractBaseTypeAdapterFactory<Period> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final ITypeAdapterFactory<Period> instance = new Factory(PeriodTypeAdapter.getInstance());

		private Factory(final TypeAdapter<Period> typeAdapter) {
			super(Period.class, typeAdapter);
		}

	}

}
