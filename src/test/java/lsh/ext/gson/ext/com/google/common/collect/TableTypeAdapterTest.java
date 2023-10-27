package lsh.ext.gson.ext.com.google.common.collect;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.params.provider.Arguments;

public final class TableTypeAdapterTest
		extends AbstractTypeAdapterTest<Table<String, String, ?>, Table<String, String, ?>> {

	private static final Gson gson = Gsons.getNormalized();

	@SuppressWarnings("unchecked")
	private static final TypeToken<Table<String, String, Integer>> stringToStringToIntegerTableTypeToken = (TypeToken<Table<String, String, Integer>>) TypeToken.getParameterized(Table.class, String.class, String.class, Integer.class);
	private static final TypeToken<Integer> integerTypeToken = TypeToken.get(Integer.class);

	@Nullable
	@Override
	protected Table<String, String, ?> normalize(@Nullable final Table<String, String, ?> value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						TableTypeAdapter.getInstance(gson.getAdapter(integerTypeToken), () -> TableTypeAdapter.Factory.builder(stringToStringToIntegerTableTypeToken, typeToken -> HashBasedTable::create)),
						"{\"A\":{\"1\":1},\"B\":{\"2\":2},\"C\":{\"3\":3}}",
						ImmutableTable.<String, String, Integer>builder()
								.put("A", "1", 1)
								.put("B", "2", 2)
								.put("C", "3", 3)
								.build()
				)
		);
	}

}
