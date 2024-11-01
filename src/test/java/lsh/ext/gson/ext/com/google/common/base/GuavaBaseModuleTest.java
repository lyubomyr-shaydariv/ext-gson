package lsh.ext.gson.ext.com.google.common.base;

import java.util.stream.Stream;

import com.google.common.base.Optional;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractModuleTest;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class GuavaBaseModuleTest
		extends AbstractModuleTest {

	private static final TypeToken<Optional<Integer>> optionalIntegerTypeToken = Types.typeTokenOf(Optional.class, Integer.class);

	public GuavaBaseModuleTest() {
		super(GuavaBaseModule.create().build());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(optionalIntegerTypeToken)
		);
	}

}
