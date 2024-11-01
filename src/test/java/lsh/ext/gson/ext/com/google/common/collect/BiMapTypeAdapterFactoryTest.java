package lsh.ext.gson.ext.com.google.common.collect;

import java.util.stream.Stream;

import com.google.common.collect.BiMap;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class BiMapTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	private static final TypeToken<BiMap<String, String>> stringToStringBiMapTypeToken = Types.typeTokenOf(BiMap.class, String.class, String.class);
	private static final TypeToken<BiMap<Integer, Object>> integerToObjectTypeToken = Types.typeTokenOf(BiMap.class, Integer.class, Object.class);
	private static final TypeToken<BiMap<Float, Integer>> floatToIntegerTypeToken = Types.typeTokenOf(BiMap.class, Float.class, Integer.class);

	public BiMapTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return GuavaCollectTypeAdapterFactory.defaultForBiMap;
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(stringToStringBiMapTypeToken),
				Arguments.of(integerToObjectTypeToken),
				Arguments.of(floatToIntegerTypeToken)
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(Types.primitiveVoidTypeToken),
				Arguments.of(Types.rawMapTypeToken),
				Arguments.of(Types.rawSetTypeToken)
		);
	}

}
