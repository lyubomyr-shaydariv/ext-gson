package lsh.ext.gson.adapters.guava;

import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.collect.BiMap;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;
import org.junit.jupiter.params.provider.Arguments;

public final class BiMapTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public BiMapTypeAdapterFactoryTest() {
		super(false);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return BiMapTypeAdapterFactory.get();
	}

	@Nonnull
	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(new TypeToken<BiMap<String, String>>() {}),
				Arguments.of(new TypeToken<BiMap<Integer, Object>>() {}),
				Arguments.of(new TypeToken<BiMap<Float, Integer>>() {})
		);
	}

	@Nonnull
	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(new TypeToken<Map<Object, Integer>>() {}),
				Arguments.of(new TypeToken<Map<Integer, String>>() {})
		);
	}

}
