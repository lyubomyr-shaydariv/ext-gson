package lsh.ext.gson.ext.com.google.common.collect;

import java.util.Map;
import java.util.stream.Stream;

import com.google.common.base.Functions;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MultimapTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public MultimapTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return MultimapTypeAdapterFactory.getInstance(GuavaCollectModule.defaultMultimapFactoryProvider, Functions.identity(), Functions.identity());
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.getParameterized(Multimap.class, String.class, String.class)),
				Arguments.of(TypeToken.getParameterized(Multimap.class, String.class, Object.class)),
				Arguments.of(TypeToken.getParameterized(Multimap.class, Integer.class, Integer.class))
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(TypeToken.getParameterized(Multiset.class, Object.class)),
				Arguments.of(TypeToken.getParameterized(Map.class, Integer.class, String.class))
		);
	}

}
