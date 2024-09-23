package lsh.ext.gson.ext.java.util;

import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class CoercedCollectionTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public CoercedCollectionTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return UtilTypeAdapterFactory.defaultForCoercedCollection;
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(Types.rawListTypeToken)
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(Types.primitiveVoidTypeToken)
		);
	}

}
