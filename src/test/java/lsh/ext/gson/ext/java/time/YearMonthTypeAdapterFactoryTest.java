package lsh.ext.gson.ext.java.time;

import java.time.YearMonth;

import com.google.gson.TypeAdapterFactory;

public final class YearMonthTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<YearMonth> {

	public YearMonthTypeAdapterFactoryTest() {
		super(YearMonth.class);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return YearMonthTypeAdapter.Factory.getInstance();
	}

}
