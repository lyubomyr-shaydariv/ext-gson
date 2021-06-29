package lsh.ext.gson.ext.java.time;

import java.time.LocalTime;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;

public final class LocalTimeTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<LocalTime> {

	public LocalTimeTypeAdapterFactoryTest() {
		super(LocalTime.class);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return LocalTimeTypeAdapterFactory.getDefaultInstance();
	}

}
