package lsh.ext.gson.ext.com.google.common.collect;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.gson.Gson;
import lsh.ext.gson.GsonBuilders;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class BiMapTypeAdapterTest
		extends AbstractTypeAdapterTest<BiMap<String, ?>, BiMap<String, ?>> {

	@Nullable
	@Override
	protected BiMap<String, ?> normalize(@Nullable final BiMap<String, ?> value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		final Gson gson = GsonBuilders.createNormalized()
				.create();
		return List.of(
				makeTestCase(
						BiMapTypeAdapter.getInstance(gson.getAdapter(String.class)),
						"{\"1\":\"foo\",\"2\":\"bar\",\"3\":\"baz\"}",
						ImmutableBiMap.of("1", "foo", "2", "bar", "3", "baz")
				)
		);
	}

}
