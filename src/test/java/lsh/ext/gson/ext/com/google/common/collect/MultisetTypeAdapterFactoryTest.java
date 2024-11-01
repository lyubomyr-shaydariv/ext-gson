package lsh.ext.gson.ext.com.google.common.collect;

import java.util.stream.Stream;

import com.google.common.collect.Multiset;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import lsh.ext.gson.test.Types;
import org.junit.jupiter.params.provider.Arguments;

public final class MultisetTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	private static final TypeToken<Multiset<String>> stringMultisetTypeToken = Types.typeTokenOf(Multiset.class, String.class);
	private static final TypeToken<Multiset<Object>> objectMultisetTypeToken = Types.typeTokenOf(Multiset.class, Object.class);
	private static final TypeToken<Multiset<Integer>> integerMultisetTypeToken = Types.typeTokenOf(Multiset.class, Integer.class);

	public MultisetTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return GuavaCollectTypeAdapterFactory.defaultForMultiset;
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(stringMultisetTypeToken),
				Arguments.of(objectMultisetTypeToken),
				Arguments.of(integerMultisetTypeToken)
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(Types.primitiveVoidTypeToken)
		);
	}

}
