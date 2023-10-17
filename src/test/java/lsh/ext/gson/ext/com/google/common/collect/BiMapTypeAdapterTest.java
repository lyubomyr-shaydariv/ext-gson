package lsh.ext.gson.ext.com.google.common.collect;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.base.Converter;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.gson.Gson;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.params.provider.Arguments;

public final class BiMapTypeAdapterTest
		extends AbstractTypeAdapterTest<BiMap<String, ?>, BiMap<String, ?>> {

	private static final Gson gson = Gsons.getNormalized();

	@Nullable
	@Override
	protected BiMap<String, ?> normalize(@Nullable final BiMap<String, ?> value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						BiMapTypeAdapterFactory.Adapter.getInstance(gson.getAdapter(String.class), HashBiMap::create, Converter.identity()),
						"{\"1\":\"foo\",\"2\":\"bar\",\"3\":\"baz\"}",
						ImmutableBiMap.of("1", "foo", "2", "bar", "3", "baz")
				)
		);
	}

}
