package lsh.ext.gson.ext.java.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.GsonBuilders;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class CoercedCollectionTypeAdapterTest
		extends AbstractTypeAdapterTest<List<?>, List<?>> {

	@Nullable
	@Override
	protected List<?> normalize(@Nullable final List<?> value) {
		return value;
	}

	@Override
	protected Stream<Arguments> makeTestCases() {
		final Gson gson = GsonBuilders.createCanonical()
				.create();
		final TypeAdapter<String> stringTypeAdapter = gson.getAdapter(TypeToken.get(String.class));
		final TypeAdapter<Integer> integerTypeAdapter = gson.getAdapter(TypeToken.get(Integer.class));
		final TypeAdapter<Boolean> booleanTypeAdapter = gson.getAdapter(TypeToken.get(Boolean.class));
		@SuppressWarnings("unchecked")
		final TypeAdapter<Map<String, Integer>> stringToIntegerMapTypeAdapter = (TypeAdapter<Map<String, Integer>>) gson.getAdapter(TypeToken.getParameterized(Map.class, String.class, Integer.class));
		return Stream.of(
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(stringToIntegerMapTypeAdapter, ArrayList::new),
						"{\"foo\":1,\"bar\":2}",
						ImmutableList.of(ImmutableMap.of("foo", 1, "bar", 2))
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(stringTypeAdapter, ArrayList::new),
						"\"foo\"",
						ImmutableList.of("foo")
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(integerTypeAdapter, ArrayList::new),
						"39",
						ImmutableList.of(39)
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(booleanTypeAdapter, ArrayList::new),
						"true",
						ImmutableList.of(true)
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(stringToIntegerMapTypeAdapter, ArrayList::new),
						"[{\"foo\":1,\"bar\":2},{\"bar\":3,\"qux\":4}]",
						ImmutableList.of(ImmutableMap.of("foo", 1, "bar", 2), ImmutableMap.of("bar", 3, "qux", 4))
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(stringTypeAdapter, ArrayList::new),
						"[\"foo\",\"bar\",\"baz\"]",
						ImmutableList.of("foo", "bar", "baz")
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(integerTypeAdapter, ArrayList::new),
						"[39,42,100]",
						ImmutableList.of(39, 42, 100)
				),
				makeTestCase(
						CoercedCollectionTypeAdapter.getInstance(booleanTypeAdapter, ArrayList::new),
						"[true,false,true]",
						ImmutableList.of(true, false, true)
				)
		);
	}

}
