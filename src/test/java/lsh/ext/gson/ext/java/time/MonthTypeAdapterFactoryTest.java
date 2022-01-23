package lsh.ext.gson.ext.java.time;

import java.time.Month;

import com.google.gson.TypeAdapterFactory;

public final class MonthTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<Month> {

	public MonthTypeAdapterFactoryTest() {
		super(Month.class);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return MonthTypeAdapterFactory.getDefaultInstance();
	}

}
