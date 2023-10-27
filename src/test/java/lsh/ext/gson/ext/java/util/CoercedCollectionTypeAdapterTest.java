package lsh.ext.gson.ext.java.util;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.Mockito;

public final class CoercedCollectionTypeAdapterTest
		extends AbstractTypeAdapterTest<List<?>, List<?>> {

	private static final Gson gson = Gsons.getNormalized();

	private static final TypeToken<String> stringTypeToken = TypeToken.get(String.class);
	private static final TypeToken<Integer> integerTypeToken = TypeToken.get(Integer.class);
	private static final TypeToken<Boolean> booleanTypeToken = TypeToken.get(Boolean.class);
	@SuppressWarnings("unchecked")
	private static final TypeToken<Map<String, Integer>> stringToIntegerMapTypeToken = (TypeToken<Map<String, Integer>>) TypeToken.getParameterized(Map.class, String.class, Integer.class);

	@Nullable
	@Override
	protected List<?> normalize(@Nullable final List<?> value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		final TypeAdapter<String> stringTypeAdapter = gson.getAdapter(stringTypeToken);
		final TypeAdapter<Integer> integerTypeAdapter = gson.getAdapter(integerTypeToken);
		final TypeAdapter<Boolean> booleanTypeAdapter = gson.getAdapter(booleanTypeToken);
		final TypeAdapter<Map<String, Integer>> stringToIntegerMapTypeAdapter = gson.getAdapter(stringToIntegerMapTypeToken);
		return List.of(
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(stringToIntegerMapTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.createBuilder(Mockito.mock())),
						"{\"foo\":1,\"bar\":2}",
						List.of(ImmutableMap.of("foo", 1, "bar", 2))
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(stringTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.createBuilder(Mockito.mock())),
						"\"foo\"",
						List.of("foo")
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(integerTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.createBuilder(Mockito.mock())),
						"39",
						List.of(39)
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(booleanTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.createBuilder(Mockito.mock())),
						"true",
						List.of(true)
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(stringToIntegerMapTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.createBuilder(Mockito.mock())),
						"[{\"foo\":1,\"bar\":2},{\"bar\":3,\"qux\":4}]",
						List.of(ImmutableMap.of("foo", 1, "bar", 2), ImmutableMap.of("bar", 3, "qux", 4))
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(stringTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.createBuilder(Mockito.mock())),
						"[\"foo\",\"bar\",\"baz\"]",
						List.of("foo", "bar", "baz")
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(integerTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.createBuilder(Mockito.mock())),
						"[39,42,100]",
						List.of(39, 42, 100)
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(booleanTypeAdapter, () -> CoercedCollectionTypeAdapter.Factory.createBuilder(Mockito.mock())),
						"[true,false,true]",
						List.of(true, false, true)
				)
		);
	}

}
