package lsh.ext.gson.ext.java.util;

import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

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
				Arguments.of(Types.optionalStringTypeToken),
				Arguments.of(Types.optionalObjectTypeToken),
				Arguments.of(Types.optionalIntegerTypeToken)
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(Types.primitiveVoidTypeToken)
		);
	}

}
