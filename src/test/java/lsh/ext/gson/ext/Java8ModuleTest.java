package lsh.ext.gson.ext;

import java.util.Optional;
import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractModuleTest;
import lsh.ext.gson.adapters.IModule;
import org.junit.jupiter.params.provider.Arguments;

public final class Java8ModuleTest
		extends AbstractModuleTest {

	public Java8ModuleTest() {
		super("Java 8");
	}

	@Override
	protected IModule createUnit() {
		return Java8Module.getDefaultInstance();
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.getParameterized(Optional.class, Integer.class)),
				Arguments.of(TypeToken.getParameterized(Stream.class, Integer.class))
		);
	}

}
