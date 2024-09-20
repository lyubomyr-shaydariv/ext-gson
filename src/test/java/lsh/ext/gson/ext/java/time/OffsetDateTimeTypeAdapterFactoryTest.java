package lsh.ext.gson.ext.java.time;

import java.time.OffsetDateTime;

import com.google.gson.TypeAdapterFactory;

public final class OffsetDateTimeTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<OffsetDateTime> {

	public OffsetDateTimeTypeAdapterFactoryTest() {
		super(OffsetDateTime.class);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return Java8TimeTypeAdapterFactory.getDefaultForOffsetDateTime();
	}

}
