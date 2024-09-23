package lsh.ext.gson.ext.com.google.common.base;

import java.util.stream.Stream;

import com.google.common.base.Optional;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	@SuppressWarnings("unchecked")
	private static final TypeToken<Optional<String>> optionalStringTypeToken = (TypeToken<Optional<String>>) TypeToken.getParameterized(Optional.class, String.class);
	@SuppressWarnings("unchecked")
	private static final TypeToken<Optional<Object>> optionalObjectTypeToken = (TypeToken<Optional<Object>>) TypeToken.getParameterized(Optional.class, Object.class);
	@SuppressWarnings("unchecked")
	private static final TypeToken<Optional<Integer>> optionalIntegerTypeToken = (TypeToken<Optional<Integer>>) TypeToken.getParameterized(Optional.class, Integer.class);

	public OptionalTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return OptionalTypeAdapterFactory.defaultForOptional;
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(optionalStringTypeToken),
				Arguments.of(optionalObjectTypeToken),
				Arguments.of(optionalIntegerTypeToken)
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(Types.primitiveVoidTypeToken)
		);
	}

}
