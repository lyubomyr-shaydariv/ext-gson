package lsh.ext.gson.adapters.java8.time;

import java.time.Period;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractToStringStringTypeAdapter;

public final class PeriodTypeAdapter
		extends AbstractToStringStringTypeAdapter<Period> {

	private static final TypeAdapter<Period> instance = new PeriodTypeAdapter();

	private PeriodTypeAdapter() {
	}

	public static TypeAdapter<Period> get() {
		return instance;
	}

	@Nonnull
	@Override
	protected Period fromString(@Nonnull final String string) {
		return Period.parse(string);
	}

}
