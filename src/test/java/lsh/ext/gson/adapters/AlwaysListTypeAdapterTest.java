package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class AlwaysListTypeAdapterTest {

	private static final TypeToken<Map<String, Integer>> stringToIntegerMapTypeToken = new TypeToken<Map<String, Integer>>() {};

	private static final Gson gson = new Gson();

	@Test
	public void testNull()
			throws IOException {
		doTest(createUnit(TypeToken.get(String.class)), "null", CoreMatchers.nullValue());
	}

	@Test
	public void testSingleObject()
			throws IOException {
		doTest(createUnit(stringToIntegerMapTypeToken), "{\"foo\":1,\"bar\":2}", CoreMatchers.is(ImmutableList.of(ImmutableMap.of("foo", 1, "bar", 2))));
	}

	@Test
	public void testSingleString()
			throws IOException {
		doTest(createUnit(TypeToken.get(String.class)), "\"foo\"", CoreMatchers.is(ImmutableList.of("foo")));
	}

	@Test
	public void testSingleNumber()
			throws IOException {
		doTest(createUnit(TypeToken.get(Integer.class)), "39", CoreMatchers.is(ImmutableList.of(39)));
	}

	@Test
	public void testSingleBoolean()
			throws IOException {
		doTest(createUnit(TypeToken.get(Boolean.class)), "true", CoreMatchers.is(ImmutableList.of(true)));
	}

	@Test
	public void testMultipleObjects()
			throws IOException {
		doTest(
				createUnit(stringToIntegerMapTypeToken),
				"[{\"foo\":1,\"bar\":2},{\"bar\":3,\"qux\":4}]",
				CoreMatchers.is(ImmutableList.of(ImmutableMap.of("foo", 1, "bar", 2), ImmutableMap.of("bar", 3, "qux", 4)))
		);
	}

	@Test
	public void testMultipleStrings()
			throws IOException {
		doTest(createUnit(TypeToken.get(String.class)), "[\"foo\",\"bar\",\"baz\"]", CoreMatchers.is(ImmutableList.of("foo", "bar", "baz")));
	}

	@Test
	public void testMultipleNumbers()
			throws IOException {
		doTest(createUnit(TypeToken.get(Integer.class)), "[39,42,100]", CoreMatchers.is(ImmutableList.of(39, 42, 100)));
	}

	@Test
	public void testMultipleBooleans()
			throws IOException {
		doTest(createUnit(TypeToken.get(Boolean.class)), "[true,false,true]", CoreMatchers.is(ImmutableList.of(true, false, true)));
	}

	private static <T> TypeAdapter<List<T>> createUnit(final TypeToken<T> typeToken) {
		return AlwaysListTypeAdapter.get(gson.getAdapter(typeToken));
	}

	private static <T> void doTest(final TypeAdapter<T> unit, final String json, final Matcher<? super T> valueMatcher)
			throws IOException {
		final T value = unit.fromJson(json);
		MatcherAssert.assertThat(value, valueMatcher);
		final String serializedJson = unit.toJson(value);
		MatcherAssert.assertThat(serializedJson, CoreMatchers.is(json));
	}

}
