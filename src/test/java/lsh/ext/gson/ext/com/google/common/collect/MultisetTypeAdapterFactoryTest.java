package lsh.ext.gson.ext.com.google.common.collect;

import java.util.stream.Stream;

import com.google.common.collect.Multiset;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MultisetTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public MultisetTypeAdapterFactoryTest() {
		super(false);
	}

	@Override
	protected TypeAdapterFactory createUnit() {
		return MultisetTypeAdapterFactory.getDefaultInstance();
	}

	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(TypeToken.getParameterized(Multiset.class, String.class)),
				Arguments.of(TypeToken.getParameterized(Multiset.class, Object.class)),
				Arguments.of(TypeToken.getParameterized(Multiset.class, Integer.class))
		);
	}

	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(TypeToken.get(Void.class))
		);
	}

}
