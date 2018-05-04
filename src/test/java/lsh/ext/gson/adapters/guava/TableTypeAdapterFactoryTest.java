package lsh.ext.gson.adapters.guava;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterFactoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class TableTypeAdapterFactoryTest
		extends AbstractTypeAdapterFactoryTest {

	@Parameterized.Parameters
	public static Iterable<TestWith> parameters() {
		return ImmutableList.of(
				testWith(
						new TypeToken<Table<String, String, String>>() {},
						new TypeToken<Table<Float, Object, Integer>>() {}),
				testWith(
						new TypeToken<Table<String, String, Object>>() {},
						new TypeToken<Table<Integer, Integer, String>>() {}),
				testWith(
						new TypeToken<Table<String, String, Integer>>() {}
				)
		);
	}

	public TableTypeAdapterFactoryTest(final TestWith testWith) {
		super(false, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapterFactory createUnit() {
		return TableTypeAdapterFactory.get();
	}

}
