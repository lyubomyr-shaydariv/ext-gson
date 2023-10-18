package lsh.ext.gson.ext.com.google.common.base;

import java.util.stream.Stream;

import com.google.common.base.Optional;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractModuleTest;
import lsh.ext.gson.IModule;
import org.junit.jupiter.params.provider.Arguments;

public final class GuavaBaseModuleTest
		extends AbstractModuleTest {

	@Override
	protected IModule createUnit() {
		return GuavaBaseModule.getInstance();
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.getParameterized(Optional.class, Integer.class))
		);
	}

}
