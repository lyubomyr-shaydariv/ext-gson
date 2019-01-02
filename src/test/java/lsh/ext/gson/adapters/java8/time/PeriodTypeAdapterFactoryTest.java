package lsh.ext.gson.adapters.java8.time;

import java.time.Period;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;

public final class PeriodTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<Period> {

	public PeriodTypeAdapterFactoryTest() {
		super(Period.class);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return PeriodTypeAdapterFactory.get();
	}

}
