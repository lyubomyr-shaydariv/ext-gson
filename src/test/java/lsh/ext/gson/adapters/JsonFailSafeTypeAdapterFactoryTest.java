package lsh.ext.gson.adapters;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class JsonFailSafeTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	@Parameterized.Parameters
	public static Iterable<TestWith> parameters() {
		return ImmutableList.of(
				testWith(
						TypeToken.get(String.class),
						TypeToken.get(int.class)
				)
		);
	}

	public JsonFailSafeTypeAdapterFactoryTest(final TestWith testWith) {
		super(true, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return JsonFailSafeTypeAdapterFactory.get();
	}

}
