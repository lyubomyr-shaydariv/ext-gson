package lsh.ext.gson.adapters;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.GsonBuilders;
import org.junit.jupiter.params.provider.Arguments;

public final class AlwaysListTypeAdapterTest
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
						AlwaysListTypeAdapter.getInstance(stringToIntegerMapTypeAdapter),
						"{\"foo\":1,\"bar\":2}",
						() -> ImmutableList.of(ImmutableMap.of("foo", 1, "bar", 2))
				),
				makeTestCase(
						AlwaysListTypeAdapter.getInstance(stringTypeAdapter),
						"\"foo\"",
						() -> ImmutableList.of("foo")
				),
				makeTestCase(
						AlwaysListTypeAdapter.getInstance(integerTypeAdapter),
						"39",
						() -> ImmutableList.of(39)
				),
				makeTestCase(
						AlwaysListTypeAdapter.getInstance(booleanTypeAdapter),
						"true",
						() -> ImmutableList.of(true)
				),
				makeTestCase(
						AlwaysListTypeAdapter.getInstance(stringToIntegerMapTypeAdapter),
						"[{\"foo\":1,\"bar\":2},{\"bar\":3,\"qux\":4}]",
						() -> ImmutableList.of(ImmutableMap.of("foo", 1, "bar", 2), ImmutableMap.of("bar", 3, "qux", 4))
				),
				makeTestCase(
						AlwaysListTypeAdapter.getInstance(stringTypeAdapter),
						"[\"foo\",\"bar\",\"baz\"]",
						(Supplier<?>) () -> ImmutableList.of("foo", "bar", "baz")
				),
				makeTestCase(
						AlwaysListTypeAdapter.getInstance(integerTypeAdapter),
						"[39,42,100]",
						(Supplier<?>) () -> ImmutableList.of(39, 42, 100)
				),
				makeTestCase(
						AlwaysListTypeAdapter.getInstance(booleanTypeAdapter),
						"[true,false,true]",
						(Supplier<?>) () -> ImmutableList.of(true, false, true)
				)
		);
	}

}
