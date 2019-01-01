package lsh.ext.gson;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public final class JsonObjectsTest {

	private static final String K1 = "foo";
	private static final String K2 = "bar";
	private static final String K3 = "baz";
	private static final String K4 = "qux";
	private static final String K5 = "quux";

	private static final JsonPrimitive l = new JsonPrimitive("L");
	private static final JsonPrimitive r = new JsonPrimitive("R");

	@Test
	public void testJsonObject() {
		MatcherAssert.assertThat(JsonObjects.of(), CoreMatchers.is(new JsonObject()));
	}

	@Test
	public void testJsonObject1() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		MatcherAssert.assertThat(JsonObjects.of(K1, JsonPrimitives.of(1)), CoreMatchers.is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		MatcherAssert.assertThat(JsonObjects.of(K1, null), CoreMatchers.is(o2));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject1AsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonObjects.of(null, JsonPrimitives.of(K1)));
	}

	@Test
	public void testJsonObject2() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		o1.addProperty(K2, 2);
		MatcherAssert.assertThat(JsonObjects.of(K1, JsonPrimitives.of(1), K2, JsonPrimitives.of(2)), CoreMatchers.is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		MatcherAssert.assertThat(JsonObjects.of(K1, null, K2, null), CoreMatchers.is(o2));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject2AsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonObjects.of(null, JsonPrimitives.of(K1), null, JsonPrimitives.of(K2)));
	}

	@Test
	public void testJsonObject3() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		o1.addProperty(K2, 2);
		o1.addProperty(K3, 3);
		MatcherAssert.assertThat(JsonObjects.of(K1, JsonPrimitives.of(1), K2, JsonPrimitives.of(2), K3, JsonPrimitives.of(3)), CoreMatchers.is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		o2.addProperty(K3, (String) null);
		MatcherAssert.assertThat(JsonObjects.of(K1, null, K2, null, K3, null), CoreMatchers.is(o2));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject3AsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonObjects.of(null, JsonPrimitives.of(K1), null, JsonPrimitives.of(K2), null, JsonPrimitives.of(K3)));
	}

	@Test
	public void testJsonObject4() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		o1.addProperty(K2, 2);
		o1.addProperty(K3, 3);
		o1.addProperty(K4, 4);
		MatcherAssert.assertThat(JsonObjects.of(K1, JsonPrimitives.of(1), K2, JsonPrimitives.of(2), K3, JsonPrimitives.of(3), K4, JsonPrimitives.of(4)), CoreMatchers.is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		o2.addProperty(K3, (String) null);
		o2.addProperty(K4, (String) null);
		MatcherAssert.assertThat(JsonObjects.of(K1, null, K2, null, K3, null, K4, null), CoreMatchers.is(o2));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject4AsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonObjects.of(null, JsonPrimitives.of(K1), null, JsonPrimitives.of(K2), null, JsonPrimitives.of(K3), null, JsonPrimitives.of(K4)));
	}

	@Test
	public void testJsonObject5() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		o1.addProperty(K2, 2);
		o1.addProperty(K3, 3);
		o1.addProperty(K4, 4);
		o1.addProperty(K5, 5);
		MatcherAssert.assertThat(JsonObjects.of(K1, JsonPrimitives.of(1), K2, JsonPrimitives.of(2), K3, JsonPrimitives.of(3), K4, JsonPrimitives.of(4), K5, JsonPrimitives.of(5)), CoreMatchers.is(o1));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		o2.addProperty(K3, (String) null);
		o2.addProperty(K4, (String) null);
		o2.addProperty(K5, (String) null);
		MatcherAssert.assertThat(JsonObjects.of(K1, null, K2, null, K3, null, K4, null, K5, null), CoreMatchers.is(o2));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonObject5AsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonObjects.of(null, JsonPrimitives.of(K1), null, JsonPrimitives.of(K2), null, JsonPrimitives.of(K3), null, JsonPrimitives.of(K4), null, JsonPrimitives.of(K5)));
	}

	@Test
	public void testFrom() {
		final JsonPrimitive element1 = JsonPrimitives.of(K1);
		final JsonPrimitive element2 = JsonPrimitives.of(K2);
		final JsonPrimitive element3 = JsonPrimitives.of(K3);
		final Map<String, ? extends JsonElement> map = ImmutableMap.of(K1, element1, K2, element2, K3, element3);
		final JsonObject jsonObject = JsonObjects.from(map);
		MatcherAssert.assertThat(jsonObject.size(), CoreMatchers.is(3));
		MatcherAssert.assertThat(jsonObject.get(K1), CoreMatchers.sameInstance(element1));
		MatcherAssert.assertThat(jsonObject.get(K2), CoreMatchers.sameInstance(element2));
		MatcherAssert.assertThat(jsonObject.get(K3), CoreMatchers.sameInstance(element3));
	}

	@Test
	public void testJsonObjectWith() {
		final JsonObject actual = JsonObjects.objectWith()
				.add(K1, "whatever")
				.add(K2, 'c')
				.add(K3, 100)
				.add(K4, false)
				.add(K5, new JsonObject())
				.get();
		final JsonObject expected = new JsonObject();
		expected.addProperty(K1, "whatever");
		expected.addProperty(K2, 'c');
		expected.addProperty(K3, 100);
		expected.addProperty(K4, false);
		expected.add(K5, new JsonObject());
		MatcherAssert.assertThat(actual, CoreMatchers.is(expected));
	}

	@Test
	public void testMergeIntoNewWithDefaultStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoNew(left, right);
		MatcherAssert.assertThat(result, MoreMatchers.refersNone(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoNew() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final IJsonObjectMergePredicate mockPredicate = Mockito.mock(IJsonObjectMergePredicate.class);
		final JsonObject result = JsonObjects.mergeIntoNew(left, right, mockPredicate);
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
		final JsonObject result = JsonObjects.mergeIntoNew(left, right, JsonObjectMergePredicates.alwaysReplaceLeft());
		MatcherAssert.assertThat(result, MoreMatchers.refersNone(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoNewWithNeverReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoNew(left, right, JsonObjectMergePredicates.neverReplaceLeft());
		MatcherAssert.assertThat(result, MoreMatchers.refersNone(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(l, l, l));
	}

	@Test
	public void testMergeIntoNewWithCustomStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoNew(left, right, (key, leftObject, leftValue, rightObject, rightValue) -> !key.equals(K1));
		MatcherAssert.assertThat(result, MoreMatchers.refersNone(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(l, r, r));
	}

	@Test
	public void testMergeIntoLeftWithDefaultStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoLeft(left, right);
		MatcherAssert.assertThat(result, MoreMatchers.refersFirst(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final IJsonObjectMergePredicate mockPredicate = Mockito.mock(IJsonObjectMergePredicate.class);
		final JsonObject result = JsonObjects.mergeIntoLeft(left, right, mockPredicate);
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
		final JsonObject result = JsonObjects.mergeIntoLeft(left, right, JsonObjectMergePredicates.alwaysReplaceLeft());
		MatcherAssert.assertThat(result, MoreMatchers.refersFirst(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(r, r, r));
	}

	@Test
	public void testMergeIntoLeftWithNeverReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoLeft(left, right, JsonObjectMergePredicates.neverReplaceLeft());
		MatcherAssert.assertThat(result, MoreMatchers.refersFirst(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(l, l, l));
	}

	@Test
	public void testMergeIntoLeftWithCustomStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoLeft(left, right, (key, leftObject, leftValue, rightObject, rightValue) -> !key.equals(K1));
		MatcherAssert.assertThat(result, MoreMatchers.refersFirst(left, right));
		MatcherAssert.assertThat(result, hasPropertiesOf(l, r, r));
	}

	@Test
	public void testAsImmutableMapCannotBeChangedViaClear() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonObject jsonObject = JsonObjects.of(K1, l, K2, r);
			final Map<String, JsonElement> map = JsonObjects.asImmutableMap(jsonObject);
			map.clear();
		});
	}

	@Test
	public void testAsMutableMapCanBeChangedViaClear() {
		final JsonObject jsonObject = JsonObjects.of(K1, l, K2, r);
		final Map<String, JsonElement> map = JsonObjects.asMutableMap(jsonObject);
		MatcherAssert.assertThat(jsonObject.size(), CoreMatchers.is(2));
		map.clear();
		MatcherAssert.assertThat(jsonObject.size(), CoreMatchers.is(0));
	}

	@Test
	public void testAsImmutableMapCannotBeChangedViaPut() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonObject jsonObject = JsonObjects.of(K1, l, K2, r);
			final Map<String, JsonElement> map = JsonObjects.asImmutableMap(jsonObject);
			map.put(K3, l);
		});
	}

	@Test
	public void testAsMutableMapCanBeChangedViaPut() {
		final JsonObject jsonObject = JsonObjects.of(K1, l, K2, r);
		final Map<String, JsonElement> map = JsonObjects.asMutableMap(jsonObject);
		MatcherAssert.assertThat(jsonObject.size(), CoreMatchers.is(2));
		MatcherAssert.assertThat(map.put(K3, l), CoreMatchers.nullValue());
		MatcherAssert.assertThat(jsonObject.size(), CoreMatchers.is(3));
		MatcherAssert.assertThat(jsonObject.get(K1), CoreMatchers.is(l));
		MatcherAssert.assertThat(jsonObject.get(K2), CoreMatchers.is(r));
		MatcherAssert.assertThat(jsonObject.get(K3), CoreMatchers.is(l));
		MatcherAssert.assertThat(map.put(K3, r), CoreMatchers.is(l));
		MatcherAssert.assertThat(jsonObject.size(), CoreMatchers.is(3));
		MatcherAssert.assertThat(jsonObject.get(K1), CoreMatchers.is(l));
		MatcherAssert.assertThat(jsonObject.get(K2), CoreMatchers.is(r));
		MatcherAssert.assertThat(jsonObject.get(K3), CoreMatchers.is(r));
	}

	@Test
	public void testAsImmutableMapCannotBeChangedViaPutAll() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonObject jsonObject = JsonObjects.of(K1, l, K2, r);
			final Map<String, JsonElement> map = JsonObjects.asImmutableMap(jsonObject);
			map.putAll(ImmutableMap.of(K3, l, K4, r));
		});
	}

	@Test
	public void testAsMutableMapCanBeChangedViaPutAll() {
		final JsonObject jsonObject = JsonObjects.of(K1, l, K2, r);
		final Map<String, JsonElement> map = JsonObjects.asMutableMap(jsonObject);
		MatcherAssert.assertThat(jsonObject.size(), CoreMatchers.is(2));
		MatcherAssert.assertThat(jsonObject.get(K1), CoreMatchers.is(l));
		MatcherAssert.assertThat(jsonObject.get(K2), CoreMatchers.is(r));
		map.putAll(ImmutableMap.of(K3, l, K4, r));
		MatcherAssert.assertThat(jsonObject.size(), CoreMatchers.is(4));
		MatcherAssert.assertThat(jsonObject.get(K1), CoreMatchers.is(l));
		MatcherAssert.assertThat(jsonObject.get(K2), CoreMatchers.is(r));
		MatcherAssert.assertThat(jsonObject.get(K3), CoreMatchers.is(l));
		MatcherAssert.assertThat(jsonObject.get(K4), CoreMatchers.is(r));
	}

	@Test
	public void testAsImmutableMapCannotBeChangedViaRemove() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonObject jsonObject = JsonObjects.of(K1, l, K2, r);
			final Map<String, JsonElement> map = JsonObjects.asImmutableMap(jsonObject);
			map.remove(K1);
		});
	}

	@Test
	public void testAsMutableMapCanBeChangedViaRemove() {
		final JsonObject jsonObject = JsonObjects.of(K1, l, K2, r);
		final Map<String, JsonElement> map = JsonObjects.asMutableMap(jsonObject);
		MatcherAssert.assertThat(map.remove(K1), CoreMatchers.is(l));
		MatcherAssert.assertThat(jsonObject.size(), CoreMatchers.is(1));
		MatcherAssert.assertThat(jsonObject.get(K2), CoreMatchers.is(r));
	}

	private static JsonObject createLeftObject() {
		return JsonObjects.of(K1, l, K2, l, K3, l);
	}

	private static JsonObject createRightObject() {
		return JsonObjects.of(K1, r, K2, r, K3, r);
	}

	private static Matcher<JsonObject> hasPropertiesOf(final JsonElement v1, final JsonElement v2, final JsonElement v3) {
		return CoreMatchers.allOf(MoreMatchers.hasProperty(K1, v1), MoreMatchers.hasProperty(K2, v2), MoreMatchers.hasProperty(K3, v3));
	}

}
