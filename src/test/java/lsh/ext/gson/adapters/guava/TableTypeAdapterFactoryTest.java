package lsh.ext.gson.adapters.guava;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
						new TypeToken<Map<Float, Integer>>() {}),
				testWith(
						new TypeToken<Table<Float, Integer, Object>>() {},
						new TypeToken<Set<String>>() {}),
				testWith(
						new TypeToken<Table<Character, List<Object>, Integer>>() {}
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
