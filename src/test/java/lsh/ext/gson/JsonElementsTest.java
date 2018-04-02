package lsh.ext.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.mockito.Mockito;

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

	private static final JsonPrimitive l = new JsonPrimitive("L");
	private static final JsonPrimitive r = new JsonPrimitive("R");

	@Test
	public void testJsonNull() {
		MatcherAssert.assertThat(JsonElements.jsonNull(), CoreMatchers.is(JsonNull.INSTANCE));
	}

	@Test
	public void testJsonPrimitiveForBooleans() {
		MatcherAssert.assertThat(JsonElements.jsonPrimitive(Boolean.TRUE), CoreMatchers.is(new JsonPrimitive(true)));
		MatcherAssert.assertThat(JsonElements.jsonPrimitive(Boolean.FALSE), CoreMatchers.is(new JsonPrimitive(false)));
	}

	@Test
	public void testJsonPrimitiveForBooleansIsFlyweight() {
		MatcherAssert.assertThat(JsonElements.jsonPrimitive(true), CoreMatchers.sameInstance(JsonElements.jsonPrimitive(Boolean.TRUE)));
		MatcherAssert.assertThat(JsonElements.jsonPrimitive(Boolean.FALSE), CoreMatchers.sameInstance(JsonElements.jsonPrimitive(false)));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitiveForBooleansAsNull() {
		JsonElements.jsonPrimitive((Boolean) null);
	}

	@Test
	public void testJsonPrimitiveForNumbers() {
		MatcherAssert.assertThat(JsonElements.jsonPrimitive((byte) 1), CoreMatchers.is(new JsonPrimitive(1)));
		MatcherAssert.assertThat(JsonElements.jsonPrimitive((short) 2), CoreMatchers.is(new JsonPrimitive(2)));
		MatcherAssert.assertThat(JsonElements.jsonPrimitive(3), CoreMatchers.is(new JsonPrimitive(3)));
		MatcherAssert.assertThat(JsonElements.jsonPrimitive(4L), CoreMatchers.is(new JsonPrimitive(4)));
		MatcherAssert.assertThat(JsonElements.jsonPrimitive(5F), CoreMatchers.is(new JsonPrimitive(5)));
		MatcherAssert.assertThat(JsonElements.jsonPrimitive(6D), CoreMatchers.is(new JsonPrimitive(6)));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitiveForNumbersAsNull() {
		JsonElements.jsonPrimitive((Number) null);
	}

	@Test
	public void testJsonPrimitivesForStrings() {
		MatcherAssert.assertThat(JsonElements.jsonPrimitive(K1), CoreMatchers.is(new JsonPrimitive(K1)));
		MatcherAssert.assertThat(JsonElements.jsonPrimitive(K2), CoreMatchers.is(new JsonPrimitive(K2)));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitivesForStringsAsNull() {
		JsonElements.jsonPrimitive((String) null);
	}

	@Test
	public void testJsonPrimitivesForCharacters() {
		MatcherAssert.assertThat(JsonElements.jsonPrimitive('f'), CoreMatchers.is(new JsonPrimitive('f')));
		MatcherAssert.assertThat(JsonElements.jsonPrimitive('b'), CoreMatchers.is(new JsonPrimitive('b')));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitivesForCharactersAsNull() {
		JsonElements.jsonPrimitive((Character) null);
	}

	@Test
	public void testJsonObject() {
		MatcherAssert.assertThat(JsonElements.jsonObject(), CoreMatchers.is(new JsonObject()));
	}

	@Test
	public void testJsonObject1() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		MatcherAssert.assertThat(JsonElements.jsonObject(K1, JsonElements.jsonPrimitive(1)), CoreMatchers.is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		MatcherAssert.assertThat(JsonElements.jsonObject(K1, null), CoreMatchers.is(o2));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject1AsNull() {
		JsonElements.jsonObject(null, JsonElements.jsonPrimitive(K1));
	}

	@Test
	public void testJsonObject2() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		o1.addProperty(K2, 2);
		MatcherAssert.assertThat(JsonElements.jsonObject(K1, JsonElements.jsonPrimitive(1), K2, JsonElements.jsonPrimitive(2)), CoreMatchers.is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		MatcherAssert.assertThat(JsonElements.jsonObject(K1, null, K2, null), CoreMatchers.is(o2));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject2AsNull() {
		JsonElements.jsonObject(null, JsonElements.jsonPrimitive(K1), null, JsonElements.jsonPrimitive(K2));
	}

	@Test
	public void testJsonObject3() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		o1.addProperty(K2, 2);
		o1.addProperty(K3, 3);
		MatcherAssert.assertThat(JsonElements.jsonObject(K1, JsonElements.jsonPrimitive(1), K2, JsonElements.jsonPrimitive(2), K3, JsonElements.jsonPrimitive(3)), CoreMatchers.is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		o2.addProperty(K3, (String) null);
		MatcherAssert.assertThat(JsonElements.jsonObject(K1, null, K2, null, K3, null), CoreMatchers.is(o2));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject3AsNull() {
		JsonElements.jsonObject(null, JsonElements.jsonPrimitive(K1), null, JsonElements.jsonPrimitive(K2), null, JsonElements.jsonPrimitive(K3));
	}

	@Test
	public void testJsonObject4() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		o1.addProperty(K2, 2);
		o1.addProperty(K3, 3);
		o1.addProperty(K4, 4);
		MatcherAssert.assertThat(JsonElements.jsonObject(K1, JsonElements.jsonPrimitive(1), K2, JsonElements.jsonPrimitive(2), K3, JsonElements.jsonPrimitive(3), K4, JsonElements.jsonPrimitive(4)), CoreMatchers.is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		o2.addProperty(K3, (String) null);
		o2.addProperty(K4, (String) null);
		MatcherAssert.assertThat(JsonElements.jsonObject(K1, null, K2, null, K3, null, K4, null), CoreMatchers.is(o2));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject4AsNull() {
		JsonElements.jsonObject(null, JsonElements.jsonPrimitive(K1), null, JsonElements.jsonPrimitive(K2), null, JsonElements.jsonPrimitive(K3), null, JsonElements.jsonPrimitive(K4));
	}

	@Test
	public void testJsonObject5() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		o1.addProperty(K2, 2);
		o1.addProperty(K3, 3);
		o1.addProperty(K4, 4);
		o1.addProperty(K5, 5);
		MatcherAssert.assertThat(JsonElements.jsonObject(K1, JsonElements.jsonPrimitive(1), K2, JsonElements.jsonPrimitive(2), K3, JsonElements.jsonPrimitive(3), K4, JsonElements.jsonPrimitive(4), K5, JsonElements.jsonPrimitive(5)), CoreMatchers.is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		o2.addProperty(K3, (String) null);
		o2.addProperty(K4, (String) null);
		o2.addProperty(K5, (String) null);
		MatcherAssert.assertThat(JsonElements.jsonObject(K1, null, K2, null, K3, null, K4, null, K5, null), CoreMatchers.is(o2));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject5AsNull() {
		JsonElements.jsonObject(null, JsonElements.jsonPrimitive(K1), null, JsonElements.jsonPrimitive(K2), null, JsonElements.jsonPrimitive(K3), null, JsonElements.jsonPrimitive(K4), null, JsonElements.jsonPrimitive(K5));
	}

	@Test
	public void testJsonObjectWith() {
		final JsonObject actual = JsonElements.jsonObjectWith()
				.add(K1, "whatever")
				.add(K2, 'c')
				.add(K3, 100)
				.add(K4, false)
				.add(K5, new JsonObject())
				.getJsonObject();
		final JsonObject expected = new JsonObject();
		expected.addProperty(K1, "whatever");
		expected.addProperty(K2, 'c');
		expected.addProperty(K3, 100);
		expected.addProperty(K4, false);
		expected.add(K5, new JsonObject());
		MatcherAssert.assertThat(actual, CoreMatchers.is(expected));
	}

	@Test
	public void testJsonArray() {
		MatcherAssert.assertThat(
				JsonElements.jsonArray(),
				CoreMatchers.is(stringJsonArray())
		);
	}

	@Test
	public void testJsonArray1() {
		MatcherAssert.assertThat(
				JsonElements.jsonArray(JsonElements.jsonPrimitive(K1)),
				CoreMatchers.is(stringJsonArray(K1))
		);
		MatcherAssert.assertThat(
				JsonElements.jsonArray(null),
				CoreMatchers.is(stringJsonArray((String) null))
		);
	}

	@Test
	public void testJsonArray2() {
		MatcherAssert.assertThat(
				JsonElements.jsonArray(JsonElements.jsonPrimitive(K1), JsonElements.jsonPrimitive(K2)),
				CoreMatchers.is(stringJsonArray(K1, K2)));
		MatcherAssert.assertThat(
				JsonElements.jsonArray(null, null),
				CoreMatchers.is(stringJsonArray(null, null))
		);
	}

	@Test
	public void testJsonArray3() {
		MatcherAssert.assertThat(
				JsonElements.jsonArray(JsonElements.jsonPrimitive(K1), JsonElements.jsonPrimitive(K2), JsonElements.jsonPrimitive(K3)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3)));
		MatcherAssert.assertThat(
				JsonElements.jsonArray(null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null))
		);
	}

	@Test
	public void testJsonArray4() {
		MatcherAssert.assertThat(
				JsonElements.jsonArray(JsonElements.jsonPrimitive(K1), JsonElements.jsonPrimitive(K2), JsonElements.jsonPrimitive(K3), JsonElements.jsonPrimitive(K4)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4)));
		MatcherAssert.assertThat(
				JsonElements.jsonArray(null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null))
		);
	}

	@Test
	public void testJsonArray5() {
		MatcherAssert.assertThat(
				JsonElements.jsonArray(JsonElements.jsonPrimitive(K1), JsonElements.jsonPrimitive(K2), JsonElements.jsonPrimitive(K3), JsonElements.jsonPrimitive(K4), JsonElements.jsonPrimitive(K5)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5)));
		MatcherAssert.assertThat(
				JsonElements.jsonArray(null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray6() {
		MatcherAssert.assertThat(
				JsonElements.jsonArray(JsonElements.jsonPrimitive(K1), JsonElements.jsonPrimitive(K2), JsonElements.jsonPrimitive(K3), JsonElements.jsonPrimitive(K4), JsonElements.jsonPrimitive(K5), JsonElements.jsonPrimitive(K6)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6)));
		MatcherAssert.assertThat(
				JsonElements.jsonArray(null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray7() {
		MatcherAssert.assertThat(
				JsonElements.jsonArray(JsonElements.jsonPrimitive(K1), JsonElements.jsonPrimitive(K2), JsonElements.jsonPrimitive(K3), JsonElements.jsonPrimitive(K4), JsonElements.jsonPrimitive(K5), JsonElements.jsonPrimitive(K6), JsonElements.jsonPrimitive(K7)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7)));
		MatcherAssert.assertThat(
				JsonElements.jsonArray(null, null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray8() {
		MatcherAssert.assertThat(
				JsonElements.jsonArray(JsonElements.jsonPrimitive(K1), JsonElements.jsonPrimitive(K2), JsonElements.jsonPrimitive(K3), JsonElements.jsonPrimitive(K4), JsonElements.jsonPrimitive(K5), JsonElements.jsonPrimitive(K6), JsonElements.jsonPrimitive(K7), JsonElements.jsonPrimitive(K8)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8)));
		MatcherAssert.assertThat(
				JsonElements.jsonArray(null, null, null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray9() {
		MatcherAssert.assertThat(
				JsonElements.jsonArray(JsonElements.jsonPrimitive(K1), JsonElements.jsonPrimitive(K2), JsonElements.jsonPrimitive(K3), JsonElements.jsonPrimitive(K4), JsonElements.jsonPrimitive(K5), JsonElements.jsonPrimitive(K6), JsonElements.jsonPrimitive(K7), JsonElements.jsonPrimitive(K8), JsonElements.jsonPrimitive(K9)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8, K9)));
		MatcherAssert.assertThat(
				JsonElements.jsonArray(null, null, null, null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray10() {
		MatcherAssert.assertThat(
				JsonElements.jsonArray(JsonElements.jsonPrimitive(K1), JsonElements.jsonPrimitive(K2), JsonElements.jsonPrimitive(K3), JsonElements.jsonPrimitive(K4), JsonElements.jsonPrimitive(K5), JsonElements.jsonPrimitive(K6), JsonElements.jsonPrimitive(K7), JsonElements.jsonPrimitive(K8), JsonElements.jsonPrimitive(K9), JsonElements.jsonPrimitive(K10)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8, K9, K10)));
		MatcherAssert.assertThat(
				JsonElements.jsonArray(null, null, null, null, null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null, null, null, null, null))
		);
	}


	@Test
	public void testJsonArrayWith() {
		final JsonArray actual = JsonElements.jsonArrayWith()
				.add('c')
				.add(true)
				.add(new JsonObject())
				.add(1000)
				.add("whatever")
				.addAll(JsonElements.jsonArray(JsonElements.jsonPrimitive(K1), JsonElements.jsonPrimitive(K2)))
				.getJsonArray();
		final JsonArray expected = new JsonArray();
		expected.add('c');
		expected.add(true);
		expected.add(new JsonObject());
		expected.add(1000);
		expected.add("whatever");
		expected.addAll(JsonElements.jsonArray(JsonElements.jsonPrimitive(K1), JsonElements.jsonPrimitive(K2)));
		MatcherAssert.assertThat(actual, CoreMatchers.is(expected));
	}

	@Test
	public void testMergeIntoNewWithDefaultStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonElements.mergeIntoNew(left, right);
		MatcherAssert.assertThat(result, MoreMatchers.refersNone(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoNew() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final IJsonObjectMergePredicate mockPredicate = Mockito.mock(IJsonObjectMergePredicate.class);
		final JsonObject result = JsonElements.mergeIntoNew(left, right, mockPredicate);
		MatcherAssert.assertThat(result, MoreMatchers.refersNone(left, right));
		Mockito.verify(mockPredicate).replace(K1, left, l, right, r);
		Mockito.verify(mockPredicate).replace(K2, left, l, right, r);
		Mockito.verify(mockPredicate).replace(K3, left, l, right, r);
		Mockito.verifyNoMoreInteractions(mockPredicate);
	}

	@Test
	public void testMergeIntoNewWithAlwaysReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonElements.mergeIntoNew(left, right, JsonObjectMergePredicates.alwaysReplaceLeft());
		MatcherAssert.assertThat(result, MoreMatchers.refersNone(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoNewWithNeverReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonElements.mergeIntoNew(left, right, JsonObjectMergePredicates.neverReplaceLeft());
		MatcherAssert.assertThat(result, MoreMatchers.refersNone(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(l, l, l));
	}

	@Test
	public void testMergeIntoNewWithCustomStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonElements.mergeIntoNew(left, right, (key, leftObject, leftValue, rightObject, rightValue) -> !key.equals(K1));
		MatcherAssert.assertThat(result, MoreMatchers.refersNone(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(l, r, r));
	}

	@Test
	public void testMergeIntoLeftWithDefaultStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonElements.mergeIntoLeft(left, right);
		MatcherAssert.assertThat(result, MoreMatchers.refersFirst(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final IJsonObjectMergePredicate mockPredicate = Mockito.mock(IJsonObjectMergePredicate.class);
		final JsonObject result = JsonElements.mergeIntoLeft(left, right, mockPredicate);
		MatcherAssert.assertThat(result, MoreMatchers.refersFirst(left, right));
		Mockito.verify(mockPredicate).replace(K1, left, l, right, r);
		Mockito.verify(mockPredicate).replace(K2, left, l, right, r);
		Mockito.verify(mockPredicate).replace(K3, left, l, right, r);
		Mockito.verifyNoMoreInteractions(mockPredicate);
	}

	@Test
	public void testMergeIntoLeftWithAlwaysReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonElements.mergeIntoLeft(left, right, JsonObjectMergePredicates.alwaysReplaceLeft());
		MatcherAssert.assertThat(result, MoreMatchers.refersFirst(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoLeftWithNeverReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonElements.mergeIntoLeft(left, right, JsonObjectMergePredicates.neverReplaceLeft());
		MatcherAssert.assertThat(result, MoreMatchers.refersFirst(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(l, l, l));
	}

	@Test
	public void testMergeIntoLeftWithCustomStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonElements.mergeIntoLeft(left, right, (key, leftObject, leftValue, rightObject, rightValue) -> !key.equals(K1));
		MatcherAssert.assertThat(result, MoreMatchers.refersFirst(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(l, r, r));
	}

	private static JsonObject createLeftObject() {
		return JsonElements.jsonObject(K1, l, K2, l, K3, l);
	}

	private static JsonObject createRightObject() {
		return JsonElements.jsonObject(K1, r, K2, r, K3, r);
	}

	private static Matcher<JsonObject> hasPropertiesOf(final JsonElement v1, final JsonElement v2, final JsonElement v3) {
		return CoreMatchers.allOf(MoreMatchers.hasProperty(K1, v1), MoreMatchers.hasProperty(K2, v2), MoreMatchers.hasProperty(K3, v3));
	}

	private static JsonArray stringJsonArray(final String... values) {
		final JsonArray array = new JsonArray();
		for ( final String value : values ) {
			array.add(value);
		}
		return array;
	}

}
