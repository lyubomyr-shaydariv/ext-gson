package lsh.ext.gson.ext.com.google.common.collect;

import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class TableTypeAdapterTest
		extends AbstractTypeAdapterTest<Table<String, String, ?>, Table<String, String, ?>> {

	@Nullable
	@Override
	protected Table<String, String, ?> finalize(@Nullable final Table<String, String, ?> value) {
		return value;
	}

	@Override
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		return Stream.of(
				test(
						TableTypeAdapter.create(gson.getAdapter(TypeToken.get(Integer.class))),
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
