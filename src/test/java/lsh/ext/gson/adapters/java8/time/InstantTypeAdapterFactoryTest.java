package lsh.ext.gson.adapters.java8.time;

import java.time.Instant;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;

public final class InstantTypeAdapterFactoryTest
		extends AbstractTimeTypeAdapterFactoryTest<Instant> {

	public InstantTypeAdapterFactoryTest() {
		super(Instant.class);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return InstantTypeAdapterFactory.get();
	}

}
