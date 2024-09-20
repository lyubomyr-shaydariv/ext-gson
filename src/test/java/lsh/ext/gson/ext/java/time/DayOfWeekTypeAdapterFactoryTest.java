package lsh.ext.gson.ext.java.time;

import java.time.DayOfWeek;

import com.google.gson.TypeAdapterFactory;

public final class DayOfWeekTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<DayOfWeek> {

	public DayOfWeekTypeAdapterFactoryTest() {
		super(DayOfWeek.class);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return Java8TimeTypeAdapterFactory.getDefaultForDayOfWeek();
	}

}
