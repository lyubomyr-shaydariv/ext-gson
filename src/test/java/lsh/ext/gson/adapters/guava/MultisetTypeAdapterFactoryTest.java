package lsh.ext.gson.adapters.guava;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multiset;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class MultisetTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	@Parameterized.Parameters
	public static Iterable<TestWith> parameters() {
		return ImmutableList.of(
				testWith(
						new TypeToken<Multiset<String>>() {}
				),
				testWith(
						new TypeToken<Multiset<Object>>() {}
				),
				testWith(
						new TypeToken<Multiset<Integer>>() {}
				)
		);
	}

	public MultisetTypeAdapterFactoryTest(final TestWith testWith) {
		super(testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return MultisetTypeAdapterFactory.get();
	}

}
