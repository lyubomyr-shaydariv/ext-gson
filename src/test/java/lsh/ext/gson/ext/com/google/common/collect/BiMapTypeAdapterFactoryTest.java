package lsh.ext.gson.ext.com.google.common.collect;

import java.util.Map;
import java.util.stream.Stream;

import com.google.common.base.Functions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import org.junit.jupiter.params.provider.Arguments;

public final class BiMapTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public BiMapTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return BiMapTypeAdapterFactory.getInstance(HashBiMap::create, Functions.identity(), Functions.identity());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.getParameterized(BiMap.class, String.class, String.class)),
				Arguments.of(TypeToken.getParameterized(BiMap.class, Integer.class, Object.class)),
				Arguments.of(TypeToken.getParameterized(BiMap.class, Float.class, Integer.class))
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(TypeToken.getParameterized(Map.class, Object.class, Integer.class)),
				Arguments.of(TypeToken.getParameterized(Map.class, Integer.class, String.class))
		);
	}

}
