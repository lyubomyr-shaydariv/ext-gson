package lsh.ext.gson.ext.java.util;

import java.util.Optional;
import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractModuleTest;
import org.junit.jupiter.params.provider.Arguments;

public final class Java8UtilModuleTest
		extends AbstractModuleTest {

	public Java8UtilModuleTest() {
		super(Java8UtilModule.getInstance());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.getParameterized(Optional.class, Integer.class))
		);
	}

}
