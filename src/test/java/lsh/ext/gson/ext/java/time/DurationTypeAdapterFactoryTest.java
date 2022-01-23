package lsh.ext.gson.ext.java.time;

import java.time.Duration;

import com.google.gson.TypeAdapterFactory;

public final class DurationTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<Duration> {

	public DurationTypeAdapterFactoryTest() {
		super(Duration.class);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return DurationTypeAdapterFactory.getInstance();
	}

}
