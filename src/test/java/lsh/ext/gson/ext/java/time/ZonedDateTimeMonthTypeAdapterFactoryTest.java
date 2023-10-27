package lsh.ext.gson.ext.java.time;

import java.time.ZonedDateTime;

import com.google.gson.TypeAdapterFactory;

public final class ZonedDateTimeMonthTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<ZonedDateTime> {

	public ZonedDateTimeMonthTypeAdapterFactoryTest() {
		super(ZonedDateTime.class);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return ZonedDateTimeTypeAdapter.Factory.getInstance();
	}

}
