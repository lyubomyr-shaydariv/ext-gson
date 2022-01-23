package lsh.ext.gson.ext.java.util;

import java.util.Optional;
import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public OptionalTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return OptionalTypeAdapterFactory.getDefaultInstance();
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.getParameterized(Optional.class, String.class)),
				Arguments.of(TypeToken.getParameterized(Optional.class, Object.class)),
				Arguments.of(TypeToken.getParameterized(Optional.class, Integer.class))
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(TypeToken.get(Void.class))
		);
	}

}
