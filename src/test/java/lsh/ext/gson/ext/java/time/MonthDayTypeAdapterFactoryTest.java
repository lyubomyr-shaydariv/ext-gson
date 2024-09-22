package lsh.ext.gson.ext.java.time;

import java.time.MonthDay;

import com.google.gson.TypeAdapterFactory;

public final class MonthDayTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<MonthDay> {

	public MonthDayTypeAdapterFactoryTest() {
		super(MonthDay.class);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return Java8TimeTypeAdapterFactory.defaultForMonthDay;
	}

}
