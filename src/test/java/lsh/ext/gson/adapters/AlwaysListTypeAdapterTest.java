package lsh.ext.gson.adapters;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.params.provider.Arguments;

public final class AlwaysListTypeAdapterTest
		extends AbstractTypeAdapterTest<List<?>> {

	public AlwaysListTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final List<?> value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		final TypeAdapter<String> stringTypeAdapter = gson.getAdapter(TypeToken.get(String.class));
		final TypeAdapter<Integer> integerTypeAdapter = gson.getAdapter(TypeToken.get(Integer.class));
		final TypeAdapter<Boolean> booleanTypeAdapter = gson.getAdapter(TypeToken.get(Boolean.class));
		final TypeAdapter<Map<String, Integer>> stringToIntegerMapTypeAdapter = gson.getAdapter(new TypeToken<Map<String, Integer>>() {});
		return Stream.of(
				Arguments.of(AlwaysListTypeAdapter.get(stringToIntegerMapTypeAdapter), "{\"foo\":1,\"bar\":2}", (Supplier<?>) () -> ImmutableList.of(ImmutableMap.of("foo", 1, "bar", 2))),
				Arguments.of(AlwaysListTypeAdapter.get(stringTypeAdapter), "\"foo\"", (Supplier<?>) () -> ImmutableList.of("foo")),
				Arguments.of(AlwaysListTypeAdapter.get(integerTypeAdapter), "39", (Supplier<?>) () -> ImmutableList.of(39)),
				Arguments.of(AlwaysListTypeAdapter.get(booleanTypeAdapter), "true", (Supplier<?>) () -> ImmutableList.of(true)),
				Arguments.of(AlwaysListTypeAdapter.get(stringToIntegerMapTypeAdapter), "[{\"foo\":1,\"bar\":2},{\"bar\":3,\"qux\":4}]", (Supplier<?>) () -> ImmutableList.of(ImmutableMap.of("foo", 1, "bar", 2), ImmutableMap.of("bar", 3, "qux", 4))),
				Arguments.of(AlwaysListTypeAdapter.get(stringTypeAdapter), "[\"foo\",\"bar\",\"baz\"]", (Supplier<?>) () -> ImmutableList.of("foo", "bar", "baz")),
				Arguments.of(AlwaysListTypeAdapter.get(integerTypeAdapter), "[39,42,100]", (Supplier<?>) () -> ImmutableList.of(39, 42, 100)),
				Arguments.of(AlwaysListTypeAdapter.get(booleanTypeAdapter), "[true,false,true]", (Supplier<?>) () -> ImmutableList.of(true, false, true))
		);
	}

}
