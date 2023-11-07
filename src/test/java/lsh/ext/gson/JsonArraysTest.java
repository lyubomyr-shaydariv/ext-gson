package lsh.ext.gson;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import lsh.ext.gson.ext.java.util.stream.JsonCollectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class JsonArraysTest {

	private static final String K1 = "foo";
	private static final String K2 = "bar";
	private static final String K3 = "baz";
	private static final String K4 = "qux";
	private static final String K5 = "quux";

	private static final JsonPrimitive foo = new JsonPrimitive("foo");
	private static final JsonPrimitive bar = new JsonPrimitive("bar");
	private static final JsonPrimitive baz = new JsonPrimitive("baz");
	private static final JsonPrimitive qux = new JsonPrimitive("qux");

	@Test
	public void testJsonArray() {
		Assertions.assertEquals(
				stringJsonArray(),
				JsonArrays.of()
		);
	}

	@Test
	public void testJsonArray1() {
		Assertions.assertEquals(
				stringJsonArray(K1),
				JsonArrays.of(JsonPrimitives.of(K1))
		);
		Assertions.assertEquals(
				stringJsonArray((String) null),
				JsonArrays.of((JsonElement) null)
		);
	}

	@Test
	public void testJsonArray2() {
		Assertions.assertEquals(
				stringJsonArray(K1, K2),
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2))
		);
		Assertions.assertEquals(
				stringJsonArray(null, null),
				JsonArrays.of(null, null)
		);
	}

	@Test
	public void testJsonArray3() {
		Assertions.assertEquals(
				stringJsonArray(K1, K2, K3),
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3))
		);
		Assertions.assertEquals(
				stringJsonArray(null, null, null),
				JsonArrays.of(null, null, null)
		);
	}

	@Test
	public void testJsonArray4() {
		Assertions.assertEquals(
				stringJsonArray(K1, K2, K3, K4),
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4))
		);
		Assertions.assertEquals(
				stringJsonArray(null, null, null, null),
				JsonArrays.of(null, null, null, null)
		);
	}

	@Test
	public void testJsonArray5() {
		Assertions.assertEquals(
				stringJsonArray(K1, K2, K3, K4, K5),
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5))
		);
		Assertions.assertEquals(
				stringJsonArray(null, null, null, null, null),
				JsonArrays.of(null, null, null, null, null)
		);
	}

	@Test
	public void testFromIterable() {
		final JsonPrimitive element1 = JsonPrimitives.of(K1);
		final JsonPrimitive element2 = JsonPrimitives.of(K2);
		final JsonPrimitive element3 = JsonPrimitives.of(K3);
		final Iterable<? extends JsonElement> jsonElements = List.of(element1, element2, element3);
		final JsonArray jsonArray = JsonArrays.from(jsonElements);
		Assertions.assertEquals(3, jsonArray.size());
		Assertions.assertSame(element1, jsonArray.get(0));
		Assertions.assertSame(element2, jsonArray.get(1));
		Assertions.assertSame(element3, jsonArray.get(2));
	}

	@Test
	public void testAsImmutableList() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		Assertions.assertEquals(3, jsonElements.size());
		Assertions.assertSame(foo, jsonElements.get(0));
		Assertions.assertSame(bar, jsonElements.get(1));
		Assertions.assertSame(baz, jsonElements.get(2));
	}

	@Test
	public void testAsImmutableListCannotBeModifiedViaAdd() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		Assertions.assertThrows(UnsupportedOperationException.class, () -> jsonElements.add(qux));
	}

	@Test
	public void testAsMutableListCanBeModifiedViaAdd() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		Assertions.assertEquals(3, jsonElements.size());
		Assertions.assertSame(foo, jsonElements.get(0));
		Assertions.assertSame(bar, jsonElements.get(1));
		Assertions.assertSame(baz, jsonElements.get(2));
		Assertions.assertTrue(jsonElements.add(qux));
		Assertions.assertEquals(4, jsonElements.size());
		Assertions.assertSame(foo, jsonElements.get(0));
		Assertions.assertSame(bar, jsonElements.get(1));
		Assertions.assertSame(baz, jsonElements.get(2));
		Assertions.assertSame(qux, jsonElements.get(3));
		Assertions.assertEquals(4, jsonArray.size());
		Assertions.assertSame(foo, jsonArray.get(0));
		Assertions.assertSame(bar, jsonArray.get(1));
		Assertions.assertSame(baz, jsonArray.get(2));
		Assertions.assertSame(qux, jsonArray.get(3));
	}

	@Test
	public void testAsImmutableListCannotBeModifiedViaAddByIndex() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		Assertions.assertThrows(UnsupportedOperationException.class, () -> jsonElements.add(0, qux));
	}

	@Test
	public void testAsMutableListCanBeModifiedViaAddByIndex() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		Assertions.assertEquals(3, jsonElements.size());
		Assertions.assertSame(foo, jsonElements.get(0));
		Assertions.assertSame(bar, jsonElements.get(1));
		Assertions.assertSame(baz, jsonElements.get(2));
		jsonElements.add(0, qux);
		Assertions.assertEquals(4, jsonElements.size());
		Assertions.assertSame(qux, jsonElements.get(0));
		Assertions.assertSame(foo, jsonElements.get(1));
		Assertions.assertSame(bar, jsonElements.get(2));
		Assertions.assertSame(baz, jsonElements.get(3));
		Assertions.assertEquals(4, jsonArray.size());
		Assertions.assertSame(qux, jsonArray.get(0));
		Assertions.assertSame(foo, jsonArray.get(1));
		Assertions.assertSame(bar, jsonArray.get(2));
		Assertions.assertSame(baz, jsonArray.get(3));
	}

	@Test
	public void testAsImmutableListCannotBeModifiedViaAddAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		Assertions.assertThrows(UnsupportedOperationException.class, () -> jsonElements.addAll(Collections.emptyList()));
	}

	@Test
	public void testAsMutableListCanBeModifiedViaAddAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		Assertions.assertEquals(2, jsonElements.size());
		Assertions.assertSame(foo, jsonElements.get(0));
		Assertions.assertEquals(bar, jsonElements.get(1));
		Assertions.assertTrue(jsonElements.addAll(List.of(baz, qux)));
		Assertions.assertEquals(4, jsonElements.size());
		Assertions.assertSame(foo, jsonElements.get(0));
		Assertions.assertSame(bar, jsonElements.get(1));
		Assertions.assertSame(baz, jsonElements.get(2));
		Assertions.assertSame(qux, jsonElements.get(3));
		Assertions.assertEquals(4, jsonArray.size());
		Assertions.assertSame(foo, jsonArray.get(0));
		Assertions.assertSame(bar, jsonArray.get(1));
		Assertions.assertSame(baz, jsonArray.get(2));
		Assertions.assertSame(qux, jsonArray.get(3));
	}

	@Test
	public void testAsImmutableListCannotBeModifiedViaAddAllViaIndex() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		Assertions.assertThrows(UnsupportedOperationException.class, () -> jsonElements.addAll(0, Collections.emptyList()));
	}

	@Test
	public void testAsMutableListCanBeModifiedViaAddAllViaIndex() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		Assertions.assertEquals(2, jsonElements.size());
		Assertions.assertSame(foo, jsonElements.get(0));
		Assertions.assertSame(bar, jsonElements.get(1));
		Assertions.assertTrue(jsonElements.addAll(0, List.of(baz, qux)));
		Assertions.assertEquals(4, jsonElements.size());
		Assertions.assertSame(baz, jsonElements.get(0));
		Assertions.assertSame(qux, jsonElements.get(1));
		Assertions.assertSame(foo, jsonElements.get(2));
		Assertions.assertSame(bar, jsonElements.get(3));
		Assertions.assertEquals(4, jsonArray.size());
		Assertions.assertEquals(baz, jsonArray.get(0));
		Assertions.assertEquals(qux, jsonArray.get(1));
		Assertions.assertEquals(foo, jsonArray.get(2));
		Assertions.assertEquals(bar, jsonArray.get(3));
	}

	@Test
	public void testAsImmutableListCannotBeModifiedViaClear() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		Assertions.assertThrows(UnsupportedOperationException.class, jsonElements::clear);
	}

	@Test
	public void testAsMutableListCanBeModifiedViaClear() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		jsonElements.clear();
		Assertions.assertEquals(0, jsonArray.size());
	}

	@Test
	public void testAsImmutableListCannotBeModifiedViaRemove() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		Assertions.assertThrows(UnsupportedOperationException.class, () -> jsonElements.remove(foo));
	}

	@Test
	public void testAsImmutableListCannotBeModifiedViaRemoveEvenIfObjectIsNotJsonElement() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		Assertions.assertThrows(UnsupportedOperationException.class, () -> jsonElements.remove("whatever"));
	}

	@Test
	public void testAsMutableListCanBeModifiedViaRemove() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		Assertions.assertTrue(jsonElements.remove(foo));
		Assertions.assertEquals(2, jsonArray.size());
		Assertions.assertSame(bar, jsonArray.get(0));
		Assertions.assertSame(baz, jsonArray.get(1));
		Assertions.assertFalse(jsonElements.remove(qux));
		Assertions.assertEquals(2, jsonArray.size());
		Assertions.assertSame(bar, jsonArray.get(0));
		Assertions.assertSame(baz, jsonArray.get(1));
	}

	@Test
	public void testAsImmutableListCannotBeModifiedViaRemoveViaIndex() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		Assertions.assertThrows(UnsupportedOperationException.class, () -> jsonElements.remove(1));
	}

	@Test
	public void testAsMutableListCanBeModifiedViaRemoveViaIndex() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		Assertions.assertSame(bar, jsonElements.remove(1));
		Assertions.assertEquals(2, jsonArray.size());
		Assertions.assertSame(foo, jsonArray.get(0));
		Assertions.assertSame(baz, jsonArray.get(1));
	}

	@Test
	public void testAsImmutableListCannotBeModifiedViaRemoveAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz, qux);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		Assertions.assertThrows(UnsupportedOperationException.class, () -> jsonElements.removeAll(List.of(bar, baz)));
	}

	@Test
	public void testAsMutableListCanBeModifiedViaRemoveAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz, qux);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		Assertions.assertTrue(jsonElements.removeAll(List.of(bar, baz)));
		Assertions.assertEquals(2, jsonArray.size());
		Assertions.assertSame(foo, jsonArray.get(0));
		Assertions.assertSame(qux, jsonArray.get(1));
		Assertions.assertFalse(jsonElements.removeAll(List.of(bar, baz)));
	}

	@Test
	public void testAsImmutableListCannotBeModifiedViaRetainAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz, qux);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		Assertions.assertThrows(UnsupportedOperationException.class, () -> jsonElements.retainAll(List.of(bar, baz)));
	}

	@Test
	public void testAsMutableListCanBeModifiedViaRetainAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz, qux);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		Assertions.assertTrue(jsonElements.retainAll(List.of(bar, baz)));
		Assertions.assertEquals(2, jsonArray.size());
		Assertions.assertSame(bar, jsonArray.get(0));
		Assertions.assertSame(baz, jsonArray.get(1));
		Assertions.assertFalse(jsonElements.retainAll(List.of(bar, baz)));
		Assertions.assertEquals(2, jsonArray.size());
		Assertions.assertSame(bar, jsonArray.get(0));
		Assertions.assertSame(baz, jsonArray.get(1));
	}

	@Test
	public void testAsImmutableListCannotBeModifiedViaSet() {
		final JsonArray jsonArray = JsonArrays.of(bar, bar, baz, qux);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		Assertions.assertThrows(UnsupportedOperationException.class, () -> jsonElements.set(0, foo));
	}

	@Test
	public void testAsMutableListCanBeModifiedViaSet() {
		final JsonArray jsonArray = JsonArrays.of(bar, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		Assertions.assertSame(bar, jsonElements.set(0, foo));
		Assertions.assertEquals(3, jsonArray.size());
		Assertions.assertSame(foo, jsonArray.get(0));
		Assertions.assertSame(bar, jsonArray.get(1));
		Assertions.assertSame(baz, jsonArray.get(2));
	}

	private static JsonArray stringJsonArray(final String... values) {
		return Stream.of(values)
				.map(JsonPrimitives::orNull)
				.collect(JsonCollectors.toJsonArray());
	}

}
