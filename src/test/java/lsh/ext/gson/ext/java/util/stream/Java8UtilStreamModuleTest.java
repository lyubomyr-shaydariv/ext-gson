package lsh.ext.gson.ext.java.util.stream;

import java.util.stream.Stream;

import lsh.ext.gson.AbstractModuleTest;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class Java8UtilStreamModuleTest
		extends AbstractModuleTest {

	public Java8UtilStreamModuleTest() {
		super(Java8UtilStreamModule.getInstance());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(Types.integerStreamTypeToken)
		);
	}

}
