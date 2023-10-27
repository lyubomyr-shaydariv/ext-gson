package lsh.ext.gson.ext.com.google.common.base;

import java.util.stream.Stream;

import com.google.common.base.Optional;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractModuleTest;
import org.junit.jupiter.params.provider.Arguments;

public final class GuavaBaseModuleTest
		extends AbstractModuleTest {

	@SuppressWarnings("unchecked")
	private static final TypeToken<Optional<Integer>> optionalIntegerTypeToken = (TypeToken<Optional<Integer>>) TypeToken.getParameterized(Optional.class, Integer.class);

	public GuavaBaseModuleTest() {
		super(GuavaBaseModule.getInstance());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(optionalIntegerTypeToken)
		);
	}

}
