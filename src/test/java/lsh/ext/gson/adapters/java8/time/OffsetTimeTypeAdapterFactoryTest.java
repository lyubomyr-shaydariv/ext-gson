package lsh.ext.gson.adapters.java8.time;

import java.time.OffsetTime;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;

public final class OffsetTimeTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<OffsetTime> {

	public OffsetTimeTypeAdapterFactoryTest() {
		super(OffsetTime.class);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return OffsetTimeTypeAdapterFactory.get();
	}

}
