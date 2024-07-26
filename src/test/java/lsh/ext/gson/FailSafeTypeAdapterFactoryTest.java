package lsh.ext.gson;

import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import org.junit.jupiter.params.provider.Arguments;

public final class FailSafeTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public FailSafeTypeAdapterFactoryTest() {
		super(true);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return FailSafeTypeAdapter.Factory.getInstance(String.class, false);
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeTokens.stringTypeToken)
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(TypeTokens.primitiveIntTypeToken)
		);
	}

}
