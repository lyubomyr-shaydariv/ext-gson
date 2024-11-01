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

	private static final TypeToken<Optional<String>> optionalStringTypeToken = Types.typeTokenOf(Optional.class, String.class);
	private static final TypeToken<Optional<Object>> optionalObjectTypeToken = Types.typeTokenOf(Optional.class, Object.class);
	private static final TypeToken<Optional<Integer>> optionalIntegerTypeToken = Types.typeTokenOf(Optional.class, Integer.class);

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
