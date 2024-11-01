package lsh.ext.gson.ext.com.google.common.collect;

import java.util.stream.Stream;

import com.google.common.collect.Multimap;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class MultimapTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	private static final TypeToken<Multimap<String, String>> stringToStringMultimapTypeToken = Types.typeTokenOf(Multimap.class, String.class, String.class);
	private static final TypeToken<Multimap<String, Object>> stringToObjectMultimapTypeToken = Types.typeTokenOf(Multimap.class, String.class, Object.class);
	private static final TypeToken<Multimap<Integer, Integer>> integerToIntegerMultimapTypeToken = Types.typeTokenOf(Multimap.class, Integer.class, Integer.class);

	public MultimapTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return GuavaCollectTypeAdapterFactory.defaultForMultimap;
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(stringToStringMultimapTypeToken),
				Arguments.of(stringToObjectMultimapTypeToken),
				Arguments.of(integerToIntegerMultimapTypeToken)
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(Types.primitiveVoidTypeToken),
				Arguments.of(Types.rawSetTypeToken),
				Arguments.of(Types.rawMapTypeToken)
		);
	}

}
