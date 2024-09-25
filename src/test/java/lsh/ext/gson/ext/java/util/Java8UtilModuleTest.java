package lsh.ext.gson.ext.java.util;

import java.util.stream.Stream;

import lsh.ext.gson.AbstractModuleTest;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class Java8UtilModuleTest
		extends AbstractModuleTest {

	public Java8UtilModuleTest() {
		super(Java8UtilModule.create().build());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(Types.optionalIntegerTypeToken),
				Arguments.of(Types.optionalIntTypeToken),
				Arguments.of(Types.optionalLongTypeToken),
				Arguments.of(Types.optionalDoubleTypeToken)
		);
	}

}
