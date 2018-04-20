package lsh.ext.gson.adapters.jsonpath;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class JsonPathTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	@Parameterized.Parameters
	public static Iterable<TestWith> parameters() {
		return ImmutableList.of(
				testWith(
						TypeToken.get(Object.class)
				)
		);
	}

	public JsonPathTypeAdapterFactoryTest(final TestWith testWith) {
		super(true, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return JsonPathTypeAdapterFactory.get();
	}

}
