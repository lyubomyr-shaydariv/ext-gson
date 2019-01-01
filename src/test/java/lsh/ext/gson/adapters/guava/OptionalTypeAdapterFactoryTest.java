package lsh.ext.gson.adapters.guava;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.base.Optional;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	public OptionalTypeAdapterFactoryTest() {
		super(false);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return OptionalTypeAdapterFactory.get();
	}

	@Nonnull
	@Override
	protected Stream<Arguments> supported() {
		return Stream.of(
				Arguments.of(new TypeToken<Optional<String>>() {}),
				Arguments.of(new TypeToken<Optional<Object>>() {}),
				Arguments.of(new TypeToken<Optional<Integer>>() {})
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
