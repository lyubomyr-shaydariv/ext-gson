package lsh.ext.gson.ext.java.time;

import java.time.Period;

import com.google.gson.TypeAdapterFactory;

public final class PeriodTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<Period> {

	public PeriodTypeAdapterFactoryTest() {
		super(Period.class);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return Java8TimeTypeAdapterFactory.defaultForPeriod;
	}

}
