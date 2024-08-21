package lsh.ext.gson.ext.java.util;

import java.util.List;
import java.util.stream.Stream;

import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class CoercedCollectionTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final TypeToken<List<?>> rawListTypeToken = (TypeToken) TypeToken.get(List.class);

	public CoercedCollectionTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return CoercedCollectionTypeAdapter.Factory.getDefaultBuilderInstance(Types.objectTypeToken);
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(rawListTypeToken)
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(Types.voidTypeToken)
		);
	}

}
