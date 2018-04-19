package lsh.ext.gson.adapters.java8;

import java.util.Optional;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;

public final class OptionalTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest<Optional<String>> {

	@Nonnull
	@Override
	protected TypeToken<Optional<String>> getTypeToken() {
		return new TypeToken<Optional<String>>() {
		};
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return OptionalTypeAdapterFactory.get();
	}

}
