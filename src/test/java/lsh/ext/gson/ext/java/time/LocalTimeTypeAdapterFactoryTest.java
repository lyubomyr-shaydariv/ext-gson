package lsh.ext.gson.ext.java.time;

import java.time.LocalTime;

import com.google.gson.TypeAdapterFactory;

public final class LocalTimeTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<LocalTime> {

	public LocalTimeTypeAdapterFactoryTest() {
		super(LocalTime.class);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return LocalTimeTypeAdapter.Factory.getInstance();
	}

}
