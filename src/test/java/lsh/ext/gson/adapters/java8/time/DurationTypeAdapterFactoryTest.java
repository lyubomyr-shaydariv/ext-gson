package lsh.ext.gson.adapters.java8.time;

import java.time.Duration;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public final class DurationTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<Duration> {

	public DurationTypeAdapterFactoryTest() {
		super(new TypeToken<Duration>() {});
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return DurationTypeAdapterFactory.get();
	}

}
