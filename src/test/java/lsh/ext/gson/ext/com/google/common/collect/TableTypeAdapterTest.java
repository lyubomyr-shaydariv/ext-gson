package lsh.ext.gson.ext.com.google.common.collect;

import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.GsonBuilders;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class TableTypeAdapterTest
		extends AbstractTypeAdapterTest<Table<String, String, ?>, Table<String, String, ?>> {

	@Nullable
	@Override
	protected Table<String, String, ?> normalize(@Nullable final Table<String, String, ?> value) {
		return value;
	}

	@Override
	protected Stream<Arguments> makeTestCases() {
		final Gson gson = GsonBuilders.createCanonical()
				.create();
		return Stream.of(
				makeTestCase(
						TableTypeAdapter.getInstance(gson.getAdapter(TypeToken.get(Integer.class))),
						"{\"A\":{\"1\":1},\"B\":{\"2\":2},\"C\":{\"3\":3}}",
						() -> ImmutableTable.<String, String, Integer>builder()
								.put("A", "1", 1)
								.put("B", "2", 2)
								.put("C", "3", 3)
								.build()
				)
		);
	}

}
