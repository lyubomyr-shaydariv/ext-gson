package lsh.ext.gson.ext.java.time;

import java.time.OffsetTime;

import com.google.gson.TypeAdapterFactory;

public final class OffsetTimeTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<OffsetTime> {

	public OffsetTimeTypeAdapterFactoryTest() {
		super(OffsetTime.class);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return Java8TimeTypeAdapterFactory.defaultForOffsetTime;
	}

}
