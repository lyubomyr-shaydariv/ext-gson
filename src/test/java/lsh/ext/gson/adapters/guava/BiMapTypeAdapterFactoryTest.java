package lsh.ext.gson.adapters.guava;

import javax.annotation.Nonnull;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableList;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class BiMapTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	@Parameterized.Parameters
	public static Iterable<TestWith> parameters() {
		return ImmutableList.of(
				testWith(
						new TypeToken<BiMap<String, String>>() {},
						new TypeToken<BiMap<Object, Integer>>() {}),
				testWith(
						new TypeToken<BiMap<String, Object>>() {},
						new TypeToken<BiMap<Integer, String>>() {}),
				testWith(
						new TypeToken<BiMap<String, Integer>>() {}
				)
		);
	}

	public BiMapTypeAdapterFactoryTest(final TestWith testWith) {
		super(false, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return BiMapTypeAdapterFactory.get();
	}

}
