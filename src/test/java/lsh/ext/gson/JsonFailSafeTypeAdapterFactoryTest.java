package lsh.ext.gson;

import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonFailSafeTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public JsonFailSafeTypeAdapterFactoryTest() {
		super(true);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return JsonFailSafeTypeAdapter.Factory.getInstance();
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
