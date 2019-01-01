package lsh.ext.gson.adapters.java8.time;

import java.time.Instant;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public final class InstantTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<Instant> {

	public InstantTypeAdapterFactoryTest() {
		super(new TypeToken<Instant>() {});
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return InstantTypeAdapterFactory.get();
	}

}
