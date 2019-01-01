package lsh.ext.gson.adapters.java8.time;

import java.time.LocalTime;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public final class LocalTimeTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<LocalTime> {

	public LocalTimeTypeAdapterFactoryTest() {
		super(new TypeToken<LocalTime>() {});
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return LocalTimeTypeAdapterFactory.get();
	}

}
