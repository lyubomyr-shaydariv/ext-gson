package lsh.ext.gson.ext.java.time;

import java.time.Month;

import com.google.gson.TypeAdapter;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lsh.ext.gson.AbstractStringTypeAdapter;
import lsh.ext.gson.ITypeAdapterFactory;

public final class MonthTypeAdapterFactory
		extends AbstractBaseTypeAdapterFactory<Month>
		implements ITypeAdapterFactory<Month> {

	@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
	private static final ITypeAdapterFactory<Month> instance = new MonthTypeAdapterFactory(Adapter.getInstance());

	private MonthTypeAdapterFactory(final TypeAdapter<Month> typeAdapter) {
		super(Month.class, typeAdapter);
	}

	public static final class Adapter
			extends AbstractStringTypeAdapter<Month> {

		@Getter(onMethod_ = { @SuppressFBWarnings("MS_EXPOSE_REP") })
		private static final TypeAdapter<Month> instance = new Adapter()
				.nullSafe();

		@Override
		protected Month fromString(final String text) {
			return Month.valueOf(text);
		}

		@Override
		protected String toString(final Month value) {
			return value.toString();
		}

	}

}
