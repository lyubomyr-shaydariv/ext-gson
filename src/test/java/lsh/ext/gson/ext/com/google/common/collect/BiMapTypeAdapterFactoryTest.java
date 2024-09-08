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

	@SuppressWarnings("unchecked")
	private static final TypeToken<BiMap<String, String>> stringToStringBiMapTypeToken = (TypeToken<BiMap<String, String>>) TypeToken.getParameterized(BiMap.class, String.class, String.class);
	@SuppressWarnings("unchecked")
	private static final TypeToken<BiMap<Integer, Object>> integerToObjectTypeToken = (TypeToken<BiMap<Integer, Object>>) TypeToken.getParameterized(BiMap.class, Integer.class, Object.class);
	@SuppressWarnings("unchecked")
	private static final TypeToken<BiMap<Float, Integer>> floatToIntegerTypeToken = (TypeToken<BiMap<Float, Integer>>) TypeToken.getParameterized(BiMap.class, Float.class, Integer.class);

	public BiMapTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return BiMapTypeAdapter.Factory.getInstance();
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
				Arguments.of(Types.voidTypeToken),
				Arguments.of(Types.rawMapTypeToken),
				Arguments.of(Types.rawSetTypeToken)
		);
	}

}
