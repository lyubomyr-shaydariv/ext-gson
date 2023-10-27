package lsh.ext.gson.ext.com.google.common.collect;

import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterFactoryTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MultimapTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	@SuppressWarnings("unchecked")
	private static final TypeToken<Multimap<String, String>> stringToStringMultimapTypeToken = (TypeToken<Multimap<String, String>>) TypeToken.getParameterized(Multimap.class, String.class, String.class);
	@SuppressWarnings("unchecked")
	private static final TypeToken<Multimap<String, Object>> stringToObjectMultimapTypeToken = (TypeToken<Multimap<String, Object>>) TypeToken.getParameterized(Multimap.class, String.class, Object.class);
	@SuppressWarnings("unchecked")
	private static final TypeToken<Multimap<Integer, Integer>> integerToIntegerMultimapTypeToken = (TypeToken<Multimap<Integer, Integer>>) TypeToken.getParameterized(Multimap.class, Integer.class, Integer.class);
	@SuppressWarnings("unchecked")
	private static final TypeToken<Multiset<Object>> objectMultisetTypeToken = (TypeToken<Multiset<Object>>) TypeToken.getParameterized(Multiset.class, Object.class);
	@SuppressWarnings("unchecked")
	private static final TypeToken<Map<Integer, String>> integerToStringMapTypeToken = (TypeToken<Map<Integer, String>>) TypeToken.getParameterized(Map.class, Integer.class, String.class);

	public MultimapTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return MultimapTypeAdapter.Factory.getInstance();
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
				Arguments.of(objectMultisetTypeToken),
				Arguments.of(integerToStringMapTypeToken)
		);
	}

}
