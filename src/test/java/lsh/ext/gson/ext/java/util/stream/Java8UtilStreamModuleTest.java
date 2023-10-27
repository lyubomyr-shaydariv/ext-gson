package lsh.ext.gson.ext.java.util.stream;

import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractModuleTest;
import org.junit.jupiter.params.provider.Arguments;

public final class Java8UtilStreamModuleTest
		extends AbstractModuleTest {

	@SuppressWarnings("unchecked")
	private static final TypeToken<Stream<Integer>> integerStreamTypeToken = (TypeToken<Stream<Integer>>) TypeToken.getParameterized(Stream.class, Integer.class);

	public Java8UtilStreamModuleTest() {
		super(Java8UtilStreamModule.getInstance());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(integerStreamTypeToken)
		);
	}

}
