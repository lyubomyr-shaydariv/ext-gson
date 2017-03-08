package lsh.ext.gson;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.hamcrest.Matcher;
import org.junit.Test;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import static lsh.ext.gson.JsonElements.jsonArray;
import static lsh.ext.gson.JsonElements.jsonNull;
import static lsh.ext.gson.JsonElements.jsonObject;
import static lsh.ext.gson.JsonElements.jsonPrimitive;
import static lsh.ext.gson.JsonElements.mergeIntoLeft;
import static lsh.ext.gson.JsonElements.mergeIntoNew;
import static lsh.ext.gson.JsonObjectMergePredicates.alwaysReplaceLeft;
import static lsh.ext.gson.JsonObjectMergePredicates.neverReplaceLeft;
import static lsh.ext.gson.MoreMatchers.hasProperty;
import static lsh.ext.gson.MoreMatchers.refersFirst;
import static lsh.ext.gson.MoreMatchers.refersNone;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
		assertThat(jsonNull(), is(JsonNull.INSTANCE));
	}

	@Test
	public void testJsonPrimitiveForBooleans() {
		assertThat(jsonPrimitive(TRUE), is(new JsonPrimitive(true)));
		assertThat(jsonPrimitive(FALSE), is(new JsonPrimitive(false)));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitiveForBooleansAsNull() {
		jsonPrimitive((Boolean) null);
	}

	@Test
	public void testJsonPrimitiveForNumbers() {
		assertThat(jsonPrimitive((byte) 1), is(new JsonPrimitive(1)));
		assertThat(jsonPrimitive((short) 2), is(new JsonPrimitive(2)));
		assertThat(jsonPrimitive(3), is(new JsonPrimitive(3)));
		assertThat(jsonPrimitive(4L), is(new JsonPrimitive(4)));
		assertThat(jsonPrimitive(5F), is(new JsonPrimitive(5)));
		assertThat(jsonPrimitive(6D), is(new JsonPrimitive(6)));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitiveForNumbersAsNull() {
		jsonPrimitive((Number) null);
	}

	@Test
	public void testJsonPrimitivesForStrings() {
		assertThat(jsonPrimitive(K1), is(new JsonPrimitive(K1)));
		assertThat(jsonPrimitive(K2), is(new JsonPrimitive(K2)));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitivesForStringsAsNull() {
		jsonPrimitive((String) null);
	}

	@Test
	public void testJsonPrimitivesForCharacters() {
		assertThat(jsonPrimitive('f'), is(new JsonPrimitive('f')));
		assertThat(jsonPrimitive('b'), is(new JsonPrimitive('b')));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitivesForCharactersAsNull() {
		jsonPrimitive((Character) null);
	}

	@Test
	public void testJsonObject() {
		assertThat(jsonObject(), is(new JsonObject()));
	}

	@Test
	public void testJsonObject1() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		assertThat(jsonObject(K1, jsonPrimitive(1)), is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		assertThat(jsonObject(K1, null), is(o2));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject1AsNull() {
		jsonObject(null, jsonPrimitive(K1));
	}

	@Test
	public void testJsonObject2() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		o1.addProperty(K2, 2);
		assertThat(jsonObject(K1, jsonPrimitive(1), K2, jsonPrimitive(2)), is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		assertThat(jsonObject(K1, null, K2, null), is(o2));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject2AsNull() {
		jsonObject(null, jsonPrimitive(K1), null, jsonPrimitive(K2));
	}

	@Test
	public void testJsonObject3() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		o1.addProperty(K2, 2);
		o1.addProperty(K3, 3);
		assertThat(jsonObject(K1, jsonPrimitive(1), K2, jsonPrimitive(2), K3, jsonPrimitive(3)), is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		o2.addProperty(K3, (String) null);
		assertThat(jsonObject(K1, null, K2, null, K3, null), is(o2));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject3AsNull() {
		jsonObject(null, jsonPrimitive(K1), null, jsonPrimitive(K2), null, jsonPrimitive(K3));
	}

	@Test
	public void testJsonObject4() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		o1.addProperty(K2, 2);
		o1.addProperty(K3, 3);
		o1.addProperty(K4, 4);
		assertThat(jsonObject(K1, jsonPrimitive(1), K2, jsonPrimitive(2), K3, jsonPrimitive(3), K4, jsonPrimitive(4)), is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		o2.addProperty(K3, (String) null);
		o2.addProperty(K4, (String) null);
		assertThat(jsonObject(K1, null, K2, null, K3, null, K4, null), is(o2));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject4AsNull() {
		jsonObject(null, jsonPrimitive(K1), null, jsonPrimitive(K2), null, jsonPrimitive(K3), null, jsonPrimitive(K4));
	}

	@Test
	public void testJsonObject5() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		o1.addProperty(K2, 2);
		o1.addProperty(K3, 3);
		o1.addProperty(K4, 4);
		o1.addProperty(K5, 5);
		assertThat(jsonObject(K1, jsonPrimitive(1), K2, jsonPrimitive(2), K3, jsonPrimitive(3), K4, jsonPrimitive(4), K5, jsonPrimitive(5)), is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		o2.addProperty(K3, (String) null);
		o2.addProperty(K4, (String) null);
		o2.addProperty(K5, (String) null);
		assertThat(jsonObject(K1, null, K2, null, K3, null, K4, null, K5, null), is(o2));
	}

	@Test(expected = NullPointerException.class)
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject5AsNull() {
		jsonObject(null, jsonPrimitive(K1), null, jsonPrimitive(K2), null, jsonPrimitive(K3), null, jsonPrimitive(K4), null, jsonPrimitive(K5));
	}

	@Test
	public void testJsonArray() {
		assertThat(
				jsonArray(),
				is(stringJsonArray())
		);
	}

	@Test
	public void testJsonArray1() {
		assertThat(
				jsonArray(jsonPrimitive(K1)),
				is(stringJsonArray(K1))
		);
		assertThat(
				jsonArray(null),
				is(stringJsonArray((String) null))
		);
	}

	@Test
	public void testJsonArray2() {
		assertThat(
				jsonArray(jsonPrimitive(K1), jsonPrimitive(K2)),
				is(stringJsonArray(K1, K2)));
		assertThat(
				jsonArray(null, null),
				is(stringJsonArray(null, null))
		);
	}

	@Test
	public void testJsonArray3() {
		assertThat(
				jsonArray(jsonPrimitive(K1), jsonPrimitive(K2), jsonPrimitive(K3)),
				is(stringJsonArray(K1, K2, K3)));
		assertThat(
				jsonArray(null, null, null),
				is(stringJsonArray(null, null, null))
		);
	}

	@Test
	public void testJsonArray4() {
		assertThat(
				jsonArray(jsonPrimitive(K1), jsonPrimitive(K2), jsonPrimitive(K3), jsonPrimitive(K4)),
				is(stringJsonArray(K1, K2, K3, K4)));
		assertThat(
				jsonArray(null, null, null, null),
				is(stringJsonArray(null, null, null, null))
		);
	}

	@Test
	public void testJsonArray5() {
		assertThat(
				jsonArray(jsonPrimitive(K1), jsonPrimitive(K2), jsonPrimitive(K3), jsonPrimitive(K4), jsonPrimitive(K5)),
				is(stringJsonArray(K1, K2, K3, K4, K5)));
		assertThat(
				jsonArray(null, null, null, null, null),
				is(stringJsonArray(null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray6() {
		assertThat(
				jsonArray(jsonPrimitive(K1), jsonPrimitive(K2), jsonPrimitive(K3), jsonPrimitive(K4), jsonPrimitive(K5), jsonPrimitive(K6)),
				is(stringJsonArray(K1, K2, K3, K4, K5, K6)));
		assertThat(
				jsonArray(null, null, null, null, null, null),
				is(stringJsonArray(null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray7() {
		assertThat(
				jsonArray(jsonPrimitive(K1), jsonPrimitive(K2), jsonPrimitive(K3), jsonPrimitive(K4), jsonPrimitive(K5), jsonPrimitive(K6), jsonPrimitive(K7)),
				is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7)));
		assertThat(
				jsonArray(null, null, null, null, null, null, null),
				is(stringJsonArray(null, null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray8() {
		assertThat(
				jsonArray(jsonPrimitive(K1), jsonPrimitive(K2), jsonPrimitive(K3), jsonPrimitive(K4), jsonPrimitive(K5), jsonPrimitive(K6), jsonPrimitive(K7), jsonPrimitive(K8)),
				is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8)));
		assertThat(
				jsonArray(null, null, null, null, null, null, null, null),
				is(stringJsonArray(null, null, null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray9() {
		assertThat(
				jsonArray(jsonPrimitive(K1), jsonPrimitive(K2), jsonPrimitive(K3), jsonPrimitive(K4), jsonPrimitive(K5), jsonPrimitive(K6), jsonPrimitive(K7), jsonPrimitive(K8), jsonPrimitive(K9)),
				is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8, K9)));
		assertThat(
				jsonArray(null, null, null, null, null, null, null, null, null),
				is(stringJsonArray(null, null, null, null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray10() {
		assertThat(
				jsonArray(jsonPrimitive(K1), jsonPrimitive(K2), jsonPrimitive(K3), jsonPrimitive(K4), jsonPrimitive(K5), jsonPrimitive(K6), jsonPrimitive(K7), jsonPrimitive(K8), jsonPrimitive(K9), jsonPrimitive(K10)),
				is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8, K9, K10)));
		assertThat(
				jsonArray(null, null, null, null, null, null, null, null, null, null),
				is(stringJsonArray(null, null, null, null, null, null, null, null, null, null))
		);
	}

	@Test
	public void testMergeIntoNewWithDefaultStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoNew(left, right);
		assertThat(result, refersNone(left, right));
		assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoNew() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final IJsonObjectMergePredicate mockPredicate = mock(IJsonObjectMergePredicate.class);
		final JsonObject result = mergeIntoNew(left, right, mockPredicate);
		assertThat(result, refersNone(left, right));
		verify(mockPredicate).replace(K1, left, l, right, r);
		verify(mockPredicate).replace(K2, left, l, right, r);
		verify(mockPredicate).replace(K3, left, l, right, r);
		verifyNoMoreInteractions(mockPredicate);
	}

	@Test
	public void testMergeIntoNewWithAlwaysReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoNew(left, right, alwaysReplaceLeft());
		assertThat(result, refersNone(left, right));
		assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoNewWithNeverReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoNew(left, right, neverReplaceLeft());
		assertThat(result, refersNone(left, right));
		assertThat(result, hasPropertiesOf(l, l, l));
	}

	@Test
	public void testMergeIntoNewWithCustomStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoNew(left, right, (key, leftObject, leftValue, rightObject, rightValue) -> !key.equals(K1));
		assertThat(result, refersNone(left, right));
		assertThat(result, hasPropertiesOf(l, r, r));
	}

	@Test
	public void testMergeIntoLeftWithDefaultStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoLeft(left, right);
		assertThat(result, refersFirst(left, right));
		assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final IJsonObjectMergePredicate mockPredicate = mock(IJsonObjectMergePredicate.class);
		final JsonObject result = mergeIntoLeft(left, right, mockPredicate);
		assertThat(result, refersFirst(left, right));
		verify(mockPredicate).replace(K1, left, l, right, r);
		verify(mockPredicate).replace(K2, left, l, right, r);
		verify(mockPredicate).replace(K3, left, l, right, r);
		verifyNoMoreInteractions(mockPredicate);
	}

	@Test
	public void testMergeIntoLeftWithAlwaysReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoLeft(left, right, alwaysReplaceLeft());
		assertThat(result, refersFirst(left, right));
		assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoLeftWithNeverReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoLeft(left, right, neverReplaceLeft());
		assertThat(result, refersFirst(left, right));
		assertThat(result, hasPropertiesOf(l, l, l));
	}

	@Test
	public void testMergeIntoLeftWithCustomStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = mergeIntoLeft(left, right, (key, leftObject, leftValue, rightObject, rightValue) -> !key.equals(K1));
		assertThat(result, refersFirst(left, right));
		assertThat(result, hasPropertiesOf(l, r, r));
	}

	private static JsonObject createLeftObject() {
		return jsonObject(K1, l, K2, l, K3, l);
	}

	private static JsonObject createRightObject() {
		return jsonObject(K1, r, K2, r, K3, r);
	}

	private static Matcher<JsonObject> hasPropertiesOf(final JsonElement v1, final JsonElement v2, final JsonElement v3) {
		return allOf(hasProperty(K1, v1), hasProperty(K2, v2), hasProperty(K3, v3));
	}

	private static JsonArray stringJsonArray(final String... values) {
		final JsonArray array = new JsonArray();
		for ( final String value : values ) {
			array.add(value);
		}
		return array;
	}

}
