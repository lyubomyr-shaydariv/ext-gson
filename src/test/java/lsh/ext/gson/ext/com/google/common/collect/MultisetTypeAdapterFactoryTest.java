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

	@SuppressWarnings("unchecked")
	private static final TypeToken<Multiset<String>> stringMultisetTypeToken = (TypeToken<Multiset<String>>) TypeToken.getParameterized(Multiset.class, String.class);
	@SuppressWarnings("unchecked")
	private static final TypeToken<Multiset<Object>> objectMultisetTypeToken = (TypeToken<Multiset<Object>>) TypeToken.getParameterized(Multiset.class, Object.class);
	@SuppressWarnings("unchecked")
	private static final TypeToken<Multiset<Integer>> integerMultisetTypeToken = (TypeToken<Multiset<Integer>>) TypeToken.getParameterized(Multiset.class, Integer.class);

	public MultisetTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return MultisetTypeAdapter.Factory.getInstance(typeToken -> {
			throw new UnsupportedOperationException(typeToken.toString());
		});
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
				Arguments.of(Types.voidTypeToken)
		);
	}

}
