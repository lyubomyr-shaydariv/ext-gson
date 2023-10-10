package lsh.ext.gson.ext.java;

import java.util.Optional;
import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractModuleTest;
import lsh.ext.gson.IModule;
import org.junit.jupiter.params.provider.Arguments;

public final class Java8ModuleTest
		extends AbstractModuleTest {

	@Override
	protected IModule createUnit() {
		return Java8Module.getInstance();
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.getParameterized(Optional.class, Integer.class)),
				Arguments.of(TypeToken.getParameterized(Stream.class, Integer.class))
		);
	}

}
