package lsh.ext.gson.ext.java.time;

import java.time.Year;

import com.google.gson.TypeAdapterFactory;

public final class YearTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<Year> {

	public YearTypeAdapterFactoryTest() {
		super(Year.class);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return YearTypeAdapterFactory.getInstance();
	}

}
