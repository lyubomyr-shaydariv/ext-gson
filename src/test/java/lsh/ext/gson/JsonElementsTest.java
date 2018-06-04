package lsh.ext.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class JsonElementsTest {

	private static final String K1 = "foo";
	private static final String K2 = "bar";
	private static final String K3 = "baz";
	private static final String K4 = "qux";
	private static final String K5 = "quux";
	private static final String K6 = "corge";
	private static final String K7 = "grault";
	private static final String K8 = "garply";
	private static final String K9 = "waldo";
	private static final String K10 = "fred";

	@Test
	public void testJsonArray() {
		MatcherAssert.assertThat(
				JsonElements.array(),
				CoreMatchers.is(stringJsonArray())
		);
	}

	@Test
	public void testJsonArray1() {
		MatcherAssert.assertThat(
				JsonElements.array(JsonPrimitives.of(K1)),
				CoreMatchers.is(stringJsonArray(K1))
		);
		MatcherAssert.assertThat(
				JsonElements.array(null),
				CoreMatchers.is(stringJsonArray((String) null))
		);
	}

	@Test
	public void testJsonArray2() {
		MatcherAssert.assertThat(
				JsonElements.array(JsonPrimitives.of(K1), JsonPrimitives.of(K2)),
				CoreMatchers.is(stringJsonArray(K1, K2)));
		MatcherAssert.assertThat(
				JsonElements.array(null, null),
				CoreMatchers.is(stringJsonArray(null, null))
		);
	}

	@Test
	public void testJsonArray3() {
		MatcherAssert.assertThat(
				JsonElements.array(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3)));
		MatcherAssert.assertThat(
				JsonElements.array(null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null))
		);
	}

	@Test
	public void testJsonArray4() {
		MatcherAssert.assertThat(
				JsonElements.array(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4)));
		MatcherAssert.assertThat(
				JsonElements.array(null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null))
		);
	}

	@Test
	public void testJsonArray5() {
		MatcherAssert.assertThat(
				JsonElements.array(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5)));
		MatcherAssert.assertThat(
				JsonElements.array(null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray6() {
		MatcherAssert.assertThat(
				JsonElements.array(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6)));
		MatcherAssert.assertThat(
				JsonElements.array(null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray7() {
		MatcherAssert.assertThat(
				JsonElements.array(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6), JsonPrimitives.of(K7)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7)));
		MatcherAssert.assertThat(
				JsonElements.array(null, null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray8() {
		MatcherAssert.assertThat(
				JsonElements.array(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6), JsonPrimitives.of(K7), JsonPrimitives.of(K8)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8)));
		MatcherAssert.assertThat(
				JsonElements.array(null, null, null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray9() {
		MatcherAssert.assertThat(
				JsonElements.array(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6), JsonPrimitives.of(K7), JsonPrimitives.of(K8), JsonPrimitives.of(K9)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8, K9)));
		MatcherAssert.assertThat(
				JsonElements.array(null, null, null, null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray10() {
		MatcherAssert.assertThat(
				JsonElements.array(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6), JsonPrimitives.of(K7), JsonPrimitives.of(K8), JsonPrimitives.of(K9), JsonPrimitives.of(K10)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8, K9, K10)));
		MatcherAssert.assertThat(
				JsonElements.array(null, null, null, null, null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null, null, null, null, null))
		);
	}


	@Test
	public void testJsonArrayWith() {
		final JsonArray actual = JsonElements.arrayWith()
				.add('c')
				.add(true)
				.add(new JsonObject())
				.add(1000)
				.add("whatever")
				.addAll(JsonElements.array(JsonPrimitives.of(K1), JsonPrimitives.of(K2)))
				.get();
		final JsonArray expected = new JsonArray();
		expected.add('c');
		expected.add(true);
		expected.add(new JsonObject());
		expected.add(1000);
		expected.add("whatever");
		expected.addAll(JsonElements.array(JsonPrimitives.of(K1), JsonPrimitives.of(K2)));
		MatcherAssert.assertThat(actual, CoreMatchers.is(expected));
	}

	private static JsonArray stringJsonArray(final String... values) {
		final JsonArray array = new JsonArray();
		for ( final String value : values ) {
			array.add(value);
		}
		return array;
	}

}
