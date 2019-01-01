package lsh.ext.gson.adapters.guava;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

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

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return MultisetTypeAdapterFactory.get();
	}

	@Nonnull
	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(new TypeToken<Multiset<String>>() {}),
				Arguments.of(new TypeToken<Multiset<Object>>() {}),
				Arguments.of(new TypeToken<Multiset<Integer>>() {})
		);
	}

	@Nonnull
	@Override
	protected Stream<Arguments> unsupported() {
		return Stream.of(
				Arguments.of(TypeToken.get(Void.class))
		);
	}

}
