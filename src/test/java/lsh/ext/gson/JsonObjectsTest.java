package lsh.ext.gson;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
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
		Assertions.assertEquals(new JsonObject(), JsonObjects.of());
	}

	@Test
	public void testJsonObject1() {
		final JsonObject o1 = new JsonObject();
		o1.addProperty(K1, 1);
		Assertions.assertEquals(o1, JsonObjects.of(K1, JsonPrimitives.of(1)));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		Assertions.assertEquals(o2, JsonObjects.of(K1, null));
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
		Assertions.assertEquals(o1, JsonObjects.of(K1, JsonPrimitives.of(1), K2, JsonPrimitives.of(2)));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		Assertions.assertEquals(o2, JsonObjects.of(K1, null, K2, null));
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
		Assertions.assertEquals(o1, JsonObjects.of(K1, JsonPrimitives.of(1), K2, JsonPrimitives.of(2), K3, JsonPrimitives.of(3)));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		o2.addProperty(K3, (String) null);
		Assertions.assertEquals(o2, JsonObjects.of(K1, null, K2, null, K3, null));
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
		Assertions.assertEquals(o1, JsonObjects.of(K1, JsonPrimitives.of(1), K2, JsonPrimitives.of(2), K3, JsonPrimitives.of(3), K4, JsonPrimitives.of(4)));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		o2.addProperty(K3, (String) null);
		o2.addProperty(K4, (String) null);
		Assertions.assertEquals(o2, JsonObjects.of(K1, null, K2, null, K3, null, K4, null));
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
		Assertions.assertEquals(o1, JsonObjects.of(K1, JsonPrimitives.of(1), K2, JsonPrimitives.of(2), K3, JsonPrimitives.of(3), K4, JsonPrimitives.of(4), K5, JsonPrimitives.of(5)));
		final JsonObject o2 = new JsonObject();
		o2.addProperty(K1, (String) null);
		o2.addProperty(K2, (String) null);
		o2.addProperty(K3, (String) null);
		o2.addProperty(K4, (String) null);
		o2.addProperty(K5, (String) null);
		Assertions.assertEquals(o2, JsonObjects.of(K1, null, K2, null, K3, null, K4, null, K5, null));
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
		final Map<String, ? extends JsonElement> map = Map.of(K1, element1, K2, element2, K3, element3);
		final JsonObject jsonObject = JsonObjects.from(map);
		Assertions.assertEquals(3, jsonObject.size());
		Assertions.assertSame(element1, jsonObject.get(K1));
		Assertions.assertSame(element2, jsonObject.get(K2));
		Assertions.assertSame(element3, jsonObject.get(K3));
	}

	@Test
	public void testMergeIntoNewWithDefaultStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoNew(left, right);
		assertRefersNone(result, left, right);
		assertHasValues(result, r, r, r);
	}

	@Test
	public void testMergeIntoNew() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final IJsonObjectMergePredicate mockPredicate = Mockito.mock(IJsonObjectMergePredicate.class);
		final JsonObject result = JsonObjects.mergeIntoNew(left, right, mockPredicate);
		assertRefersNone(result, left, right);
		Mockito.verify(mockPredicate).canReplace(K1, left, l, right, r);
		Mockito.verify(mockPredicate).canReplace(K2, left, l, right, r);
		Mockito.verify(mockPredicate).canReplace(K3, left, l, right, r);
		Mockito.verifyNoMoreInteractions(mockPredicate);
	}

	@Test
	public void testMergeIntoNewWithAlwaysReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoNew(left, right, IJsonObjectMergePredicate.alwaysReplaceLeft());
		assertRefersNone(result, left, right);
		assertHasValues(result, r, r, r);
	}

	@Test
	public void testMergeIntoNewWithNeverReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoNew(left, right, IJsonObjectMergePredicate.neverReplaceLeft());
		assertRefersNone(result, left, right);
		assertHasValues(result, l, l, l);
	}

	@Test
	public void testMergeIntoNewWithCustomStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoNew(left, right, (key, leftObject, leftValue, rightObject, rightValue) -> !key.equals(K1));
		assertRefersNone(result, left, right);
		assertHasValues(result, l, r, r);
	}

	@Test
	public void testMergeIntoLeftWithDefaultStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoLeft(left, right);
		assertRefersFirst(result, left, right);
		assertHasValues(result, r, r, r);
	}

	@Test
	public void testMergeIntoLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final IJsonObjectMergePredicate mockPredicate = Mockito.mock(IJsonObjectMergePredicate.class);
		final JsonObject result = JsonObjects.mergeIntoLeft(left, right, mockPredicate);
		assertRefersFirst(result, left, right);
		Mockito.verify(mockPredicate).canReplace(K1, left, l, right, r);
		Mockito.verify(mockPredicate).canReplace(K2, left, l, right, r);
		Mockito.verify(mockPredicate).canReplace(K3, left, l, right, r);
		Mockito.verifyNoMoreInteractions(mockPredicate);
	}

	@Test
	public void testMergeIntoLeftWithAlwaysReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoLeft(left, right, IJsonObjectMergePredicate.alwaysReplaceLeft());
		assertRefersFirst(result, left, right);
		assertHasValues(result, r, r, r);
	}

	@Test
	public void testMergeIntoLeftWithNeverReplaceLeft() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoLeft(left, right, IJsonObjectMergePredicate.neverReplaceLeft());
		assertRefersFirst(result, left, right);
		assertHasValues(result, l, l, l);
	}

	@Test
	public void testMergeIntoLeftWithCustomStrategy() {
		final JsonObject left = createLeftObject();
		final JsonObject right = createRightObject();
		final JsonObject result = JsonObjects.mergeIntoLeft(left, right, (key, leftObject, leftValue, rightObject, rightValue) -> !key.equals(K1));
		assertRefersFirst(result, left, right);
		assertHasValues(result, l, r, r);
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
		Assertions.assertEquals(2, jsonObject.size());
		map.clear();
		Assertions.assertEquals(0, jsonObject.size());
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
		Assertions.assertEquals(2, jsonObject.size());
		Assertions.assertNull(map.put(K3, l));
		Assertions.assertEquals(3, jsonObject.size());
		Assertions.assertEquals(l, jsonObject.get(K1));
		Assertions.assertEquals(r, jsonObject.get(K2));
		Assertions.assertEquals(l, jsonObject.get(K3));
		Assertions.assertEquals(l, map.put(K3, r));
		Assertions.assertEquals(3, jsonObject.size());
		Assertions.assertEquals(l, jsonObject.get(K1));
		Assertions.assertEquals(r, jsonObject.get(K2));
		Assertions.assertEquals(r, jsonObject.get(K3));
	}

	@Test
	public void testAsImmutableMapCannotBeChangedViaPutAll() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonObject jsonObject = JsonObjects.of(K1, l, K2, r);
			final Map<String, JsonElement> map = JsonObjects.asImmutableMap(jsonObject);
			map.putAll(Map.of(K3, l, K4, r));
		});
	}

	@Test
	public void testAsMutableMapCanBeChangedViaPutAll() {
		final JsonObject jsonObject = JsonObjects.of(K1, l, K2, r);
		final Map<String, JsonElement> map = JsonObjects.asMutableMap(jsonObject);
		Assertions.assertEquals(2, jsonObject.size());
		Assertions.assertEquals(l, jsonObject.get(K1));
		Assertions.assertEquals(r, jsonObject.get(K2));
		map.putAll(Map.of(K3, l, K4, r));
		Assertions.assertEquals(4, jsonObject.size());
		Assertions.assertEquals(l, jsonObject.get(K1));
		Assertions.assertEquals(r, jsonObject.get(K2));
		Assertions.assertEquals(l, jsonObject.get(K3));
		Assertions.assertEquals(r, jsonObject.get(K4));
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
		Assertions.assertEquals(l, map.remove(K1));
		Assertions.assertEquals(1, jsonObject.size());
		Assertions.assertEquals(r, jsonObject.get(K2));
	}

	private static JsonObject createLeftObject() {
		return JsonObjects.of(K1, l, K2, l, K3, l);
	}

	private static JsonObject createRightObject() {
		return JsonObjects.of(K1, r, K2, r, K3, r);
	}

	private static void assertHasValues(final JsonObject o, final JsonElement e1, final JsonElement e2, final JsonElement e3) {
		Assertions.assertEquals(e1, o.get(K1));
		Assertions.assertEquals(e2, o.get(K2));
		Assertions.assertEquals(e3, o.get(K3));
	}

	private static void assertRefersNone(final JsonElement o, final JsonElement... es) {
		for ( final JsonElement e : es ) {
			Assertions.assertNotSame(o, e);
		}
	}

	private static void assertRefersFirst(final JsonElement o, final JsonElement e1, final JsonElement... es) {
		Assertions.assertSame(o, e1);
		assertRefersNone(o, es);
	}

}
