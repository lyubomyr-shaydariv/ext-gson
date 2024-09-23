package lsh.ext.gson.ext.com.google.common.collect;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.test.TestTypeAdapters;
import org.junit.jupiter.params.provider.Arguments;

public final class BiMapTypeAdapterTest
		extends AbstractTypeAdapterTest<BiMap<String, ?>, BiMap<String, ?>> {

	@SuppressWarnings("unchecked")
	private static final TypeToken<BiMap<String, String>> stringToStringBiMapType = (TypeToken<BiMap<String, String>>) TypeToken.getParameterized(HashBiMap.class, String.class, String.class);

	@Nullable
	@Override
	protected BiMap<String, ?> normalize(@Nullable final BiMap<String, ?> value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						BiMapTypeAdapter.getInstance(TestTypeAdapters.stringTypeAdapter, GuavaCollectTypeAdapterFactory.defaultBuilderFactoryForBiMap(stringToStringBiMapType)),
						"{\"1\":\"foo\",\"2\":\"bar\",\"3\":\"baz\"}",
						ImmutableBiMap.of("1", "foo", "2", "bar", "3", "baz")
				)
		);
	}

}
