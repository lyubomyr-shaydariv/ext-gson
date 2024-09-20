package lsh.ext.gson.ext.java.time;

import java.time.Instant;

import com.google.gson.TypeAdapterFactory;

public final class InstantTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<Instant> {

	public InstantTypeAdapterFactoryTest() {
		super(Instant.class);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return Java8TimeTypeAdapterFactory.getDefaultForInstant();
	}

}
