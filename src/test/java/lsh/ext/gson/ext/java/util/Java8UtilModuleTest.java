package lsh.ext.gson.ext.java.util;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractModuleTest;
import org.junit.jupiter.params.provider.Arguments;

public final class Java8UtilModuleTest
		extends AbstractModuleTest {

	@SuppressWarnings("unchecked")
	private static final TypeToken<Optional<Integer>> optionalIntegerTypeToken = (TypeToken<Optional<Integer>>) TypeToken.getParameterized(Optional.class, Integer.class);

	private static final TypeToken<OptionalInt> optionalIntTypeToken = TypeToken.get(OptionalInt.class);

	private static final TypeToken<OptionalLong> optionalLongTypeToken = TypeToken.get(OptionalLong.class);

	private static final TypeToken<OptionalDouble> optionalDoubleTypeToken = TypeToken.get(OptionalDouble.class);

	public Java8UtilModuleTest() {
		super(Java8UtilModule.getInstance());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(optionalIntegerTypeToken),
				Arguments.of(optionalIntTypeToken),
				Arguments.of(optionalLongTypeToken),
				Arguments.of(optionalDoubleTypeToken)
		);
	}

}
