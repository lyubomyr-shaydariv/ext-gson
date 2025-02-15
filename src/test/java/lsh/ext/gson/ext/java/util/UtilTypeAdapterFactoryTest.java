package lsh.ext.gson.ext.java.util;

import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class UtilTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public UtilTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return UtilTypeAdapterFactory.defaultForMap;
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(Types.rawMapTypeToken)
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(Types.rawSetTypeToken),
				Arguments.of(Types.integerListTypeToken)
		);
	}

}
