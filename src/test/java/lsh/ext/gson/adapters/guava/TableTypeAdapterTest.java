package lsh.ext.gson.adapters.guava;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class TableTypeAdapterTest
		extends AbstractTypeAdapterTest<Table<String, String, ?>> {

	public TableTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Table<String, String, ?> value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		return Stream.of(
				test(
						TableTypeAdapter.get(gson.getAdapter(TypeToken.get(Integer.class))),
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
