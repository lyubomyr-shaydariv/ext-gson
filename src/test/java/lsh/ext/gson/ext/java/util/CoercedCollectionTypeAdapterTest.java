package lsh.ext.gson.ext.java.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import lsh.ext.gson.TypeTokens;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.Mockito;

public final class CoercedCollectionTypeAdapterTest
		extends AbstractTypeAdapterTest<List<?>, List<?>> {

	private static final Gson gson = Gsons.getNormalized();

	@SuppressWarnings("unchecked")
	private static final TypeToken<Map<String, Integer>> stringToIntegerMapTypeToken = (TypeToken<Map<String, Integer>>) TypeToken.getParameterized(Map.class, String.class, Integer.class);

	@Nullable
	@Override
	protected List<?> normalize(@Nullable final List<?> value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		final TypeAdapter<String> stringTypeAdapter = gson.getAdapter(TypeTokens.stringTypeToken);
		final TypeAdapter<Integer> integerTypeAdapter = gson.getAdapter(TypeTokens.integerTypeToken);
		final TypeAdapter<Boolean> booleanTypeAdapter = gson.getAdapter(TypeTokens.booleanTypeToken);
		final TypeAdapter<Map<String, Integer>> stringToIntegerMapTypeAdapter = gson.getAdapter(stringToIntegerMapTypeToken);
		return List.of(
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(stringToIntegerMapTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.builder(Mockito.mock(), typeToken -> ArrayList::new)),
						"{\"foo\":1,\"bar\":2}",
						List.of(ImmutableMap.of("foo", 1, "bar", 2))
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(stringTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.builder(Mockito.mock(), typeToken -> ArrayList::new)),
						"\"foo\"",
						List.of("foo")
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(integerTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.builder(Mockito.mock(), typeToken -> ArrayList::new)),
						"39",
						List.of(39)
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(booleanTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.builder(Mockito.mock(), typeToken -> ArrayList::new)),
						"true",
						List.of(true)
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(stringToIntegerMapTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.builder(Mockito.mock(), typeToken -> ArrayList::new)),
						"[{\"foo\":1,\"bar\":2},{\"bar\":3,\"qux\":4}]",
						List.of(ImmutableMap.of("foo", 1, "bar", 2), ImmutableMap.of("bar", 3, "qux", 4))
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(stringTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.builder(Mockito.mock(), typeToken -> ArrayList::new)),
						"[\"foo\",\"bar\",\"baz\"]",
						List.of("foo", "bar", "baz")
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(integerTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.builder(Mockito.mock(), typeToken -> ArrayList::new)),
						"[39,42,100]",
						List.of(39, 42, 100)
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(booleanTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.builder(Mockito.mock(), typeToken -> ArrayList::new)),
						"[true,false,true]",
						List.of(true, false, true)
				)
		);
	}

}
