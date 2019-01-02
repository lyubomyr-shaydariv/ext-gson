package lsh.ext.gson.adapters.java8.time;

import java.time.OffsetDateTime;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;

public final class OffsetDateTimeTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<OffsetDateTime> {

	public OffsetDateTimeTypeAdapterFactoryTest() {
		super(OffsetDateTime.class);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return OffsetDateTimeTypeAdapterFactory.get();
	}

}
