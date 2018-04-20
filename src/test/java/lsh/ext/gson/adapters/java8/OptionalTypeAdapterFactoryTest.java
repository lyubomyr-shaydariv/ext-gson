package lsh.ext.gson.adapters.java8;

import java.util.Optional;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class OptionalTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	@Parameterized.Parameters
	public static Iterable<TestWith> parameters() {
		return ImmutableList.of(
				testWith(
						new TypeToken<Optional<String>>() {}
				),
				testWith(
						new TypeToken<Optional<Object>>() {}
				),
				testWith(
						new TypeToken<Optional<Integer>>() {}
				)
		);
	}

	public OptionalTypeAdapterFactoryTest(final TestWith testWith) {
		super(testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return OptionalTypeAdapterFactory.get();
	}

}
