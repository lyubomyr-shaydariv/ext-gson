package lsh.ext.gson.adapters.guava;

import java.util.Collection;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class MultimapTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	@Parameterized.Parameters
	public static Collection<TestWith> parameters() {
		return ImmutableList.of(
				testWith(new TypeToken<Multimap<String, String>>() {}, new TypeToken<Multimap<Object, Integer>>() {}),
				testWith(new TypeToken<Multimap<String, Object>>() {}, new TypeToken<Multimap<Integer, String>>() {}),
				testWith(new TypeToken<Multimap<String, Integer>>() {})
		);
	}

	public MultimapTypeAdapterFactoryTest(final TestWith testWith) {
		super(testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return MultimapTypeAdapterFactory.get();
	}

}
