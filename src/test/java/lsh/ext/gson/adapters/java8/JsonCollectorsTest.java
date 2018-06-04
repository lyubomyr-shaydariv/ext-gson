package lsh.ext.gson.adapters.java8;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lsh.ext.gson.JsonElements;
import lsh.ext.gson.JsonObjects;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class JsonCollectorsTest {

	private static final String K1 = "foo-key";
	private static final String K2 = "bar-key";
	private static final String K3 = "baz-key";

	private static final JsonPrimitive v1 = new JsonPrimitive("foo-value");
	private static final JsonPrimitive v2 = new JsonPrimitive("bar-value");
	private static final JsonPrimitive v3 = new JsonPrimitive("baz-value");

	@Test
	public void testToNewJsonObject() {
		final JsonObject actual = ImmutableMap.of(K1, v1, K2, v2, K3, v3)
				.entrySet()
				.stream()
				.collect(JsonCollectors.toJsonObject());
		final JsonObject expected = JsonObjects.of(K1, v1, K2, v2, K3, v3);
		MatcherAssert.assertThat(actual, CoreMatchers.is(expected));
	}

	@Test
	public void testToExistingJsonObject() {
		final JsonObject actual = ImmutableMap.of(K2, v2, K3, v3)
				.entrySet()
				.stream()
				.collect(JsonCollectors.toJsonObject(() -> JsonObjects.of(K1, v1)));
		final JsonObject expected = JsonObjects.of(K1, v1, K2, v2, K3, v3);
		MatcherAssert.assertThat(actual, CoreMatchers.is(expected));
	}

	@Test
	public void testToExistingJsonObjectOverwritesEntries() {
		final JsonObject actual = ImmutableMap.of(K1, v1, K2, v2, K3, v3)
				.entrySet()
				.stream()
				.collect(JsonCollectors.toJsonObject(() -> JsonObjects.of(K1, v2, K2, v3, K3, v2)));
		final JsonObject expected = JsonObjects.of(K1, v1, K2, v2, K3, v3);
		MatcherAssert.assertThat(actual, CoreMatchers.is(expected));
	}

	@Test
	public void testToNewJsonArray() {
		final JsonArray actual = ImmutableList.of(v1, v2, v3)
				.stream()
				.collect(JsonCollectors.toJsonArray());
		final JsonArray expected = JsonElements.array(v1, v2, v3);
		MatcherAssert.assertThat(actual, CoreMatchers.is(expected));
	}

	@Test
	public void testToExistingJsonArray() {
		final JsonArray actual = ImmutableList.of(v2, v3)
				.stream()
				.collect(JsonCollectors.toJsonArray(() -> JsonElements.array(v1)));
		final JsonArray expected = JsonElements.array(v1, v2, v3);
		MatcherAssert.assertThat(actual, CoreMatchers.is(expected));
	}

}
