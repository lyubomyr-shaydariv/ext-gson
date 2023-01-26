package lsh.ext.gson;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public final class JsonArraysTest {

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
				JsonArrays.of(null)
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
	public void testJsonArray6() {
		Assertions.assertEquals(
				stringJsonArray(K1, K2, K3, K4, K5, K6),
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6))
		);
		Assertions.assertEquals(
				stringJsonArray(null, null, null, null, null, null),
				JsonArrays.of(null, null, null, null, null, null)
		);
	}

	@Test
	public void testJsonArray7() {
		Assertions.assertEquals(
				stringJsonArray(K1, K2, K3, K4, K5, K6, K7),
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6), JsonPrimitives.of(K7))
		);
		Assertions.assertEquals(
				stringJsonArray(null, null, null, null, null, null, null),
				JsonArrays.of(null, null, null, null, null, null, null)
		);
	}

	@Test
	public void testJsonArray8() {
		Assertions.assertEquals(
				stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8),
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6), JsonPrimitives.of(K7), JsonPrimitives.of(K8))
		);
		Assertions.assertEquals(
				stringJsonArray(null, null, null, null, null, null, null, null),
				JsonArrays.of(null, null, null, null, null, null, null, null)
		);
	}

	@Test
	public void testJsonArray9() {
		Assertions.assertEquals(
				stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8, K9),
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6), JsonPrimitives.of(K7), JsonPrimitives.of(K8), JsonPrimitives.of(K9))
		);
		Assertions.assertEquals(
				stringJsonArray(null, null, null, null, null, null, null, null, null),
				JsonArrays.of(null, null, null, null, null, null, null, null, null)
		);
	}

	@Test
	public void testJsonArray10() {
		Assertions.assertEquals(
				stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8, K9, K10),
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6), JsonPrimitives.of(K7), JsonPrimitives.of(K8), JsonPrimitives.of(K9), JsonPrimitives.of(K10))
		);
		Assertions.assertEquals(
				stringJsonArray(null, null, null, null, null, null, null, null, null, null),
				JsonArrays.of(null, null, null, null, null, null, null, null, null, null)
		);
	}

	@Test
	public void testFromIterable() {
		final JsonPrimitive element1 = JsonPrimitives.of(K1);
		final JsonPrimitive element2 = JsonPrimitives.of(K2);
		final JsonPrimitive element3 = JsonPrimitives.of(K3);
		final Iterable<? extends JsonElement> jsonElements = ImmutableList.of(element1, element2, element3);
		final JsonArray jsonArray = JsonArrays.from(jsonElements);
		Assertions.assertEquals(3, jsonArray.size());
		Assertions.assertSame(element1, jsonArray.get(0));
		Assertions.assertSame(element2, jsonArray.get(1));
		Assertions.assertSame(element3, jsonArray.get(2));
	}

	@Test
	public void testFromCollection() {
		final JsonPrimitive element1 = JsonPrimitives.of(K1);
		final JsonPrimitive element2 = JsonPrimitives.of(K2);
		final JsonPrimitive element3 = JsonPrimitives.of(K3);
		final List<? extends JsonElement> jsonElements = ImmutableList.of(element1, element2, element3);
		final List<? extends JsonElement> spyJsonElements = Mockito.spy(jsonElements);
		final JsonArray jsonArray = JsonArrays.from(spyJsonElements);
		Assertions.assertEquals(3, jsonArray.size());
		Assertions.assertSame(element1, jsonArray.get(0));
		Assertions.assertSame(element2, jsonArray.get(1));
		Assertions.assertSame(element3, jsonArray.get(2));
		Mockito.verify(spyJsonElements).size();
		Mockito.verify(spyJsonElements).iterator();
		Mockito.verify(spyJsonElements).listIterator();
		Mockito.verify(spyJsonElements).listIterator(0);
		Mockito.verifyNoMoreInteractions(spyJsonElements);
	}

	@Test
	public void testJsonArrayWith() {
		final JsonArray actual = JsonArrays.arrayWith()
				.add('c')
				.add(true)
				.add(new JsonObject())
				.add(1000)
				.add("whatever")
				.addAll(JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2)))
				.get();
		final JsonArray expected = new JsonArray();
		expected.add('c');
		expected.add(true);
		expected.add(new JsonObject());
		expected.add(1000);
		expected.add("whatever");
		expected.addAll(JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2)));
		Assertions.assertEquals(expected, actual);
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
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
			final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
			jsonElements.add(qux);
		});
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
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
			final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
			jsonElements.add(0, qux);
		});
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
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
			final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
			jsonElements.addAll(Collections.emptyList());
		});
	}

	@Test
	public void testAsMutableListCanBeModifiedViaAddAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		Assertions.assertEquals(2, jsonElements.size());
		Assertions.assertSame(foo, jsonElements.get(0));
		Assertions.assertEquals(bar, jsonElements.get(1));
		Assertions.assertTrue(jsonElements.addAll(ImmutableList.of(baz, qux)));
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
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
			final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
			jsonElements.addAll(0, Collections.emptyList());
		});
	}

	@Test
	public void testAsMutableListCanBeModifiedViaAddAllViaIndex() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		Assertions.assertEquals(2, jsonElements.size());
		Assertions.assertSame(foo, jsonElements.get(0));
		Assertions.assertSame(bar, jsonElements.get(1));
		Assertions.assertTrue(jsonElements.addAll(0, ImmutableList.of(baz, qux)));
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
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
			final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
			jsonElements.clear();
		});
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
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
			final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
			jsonElements.remove(foo);
		});
	}

	@Test
	public void testAsImmutableListCannotBeModifiedViaRemoveEvenIfObjectIsNotJsonElement() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
			final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
			jsonElements.remove("whatever");
		});
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
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
			final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
			jsonElements.remove(1);
		});
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
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonArray jsonArray = JsonArrays.of(foo, bar, baz, qux);
			final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
			jsonElements.removeAll(ImmutableList.of(bar, baz));
		});
	}

	@Test
	public void testAsMutableListCanBeModifiedViaRemoveAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz, qux);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		Assertions.assertTrue(jsonElements.removeAll(ImmutableList.of(bar, baz)));
		Assertions.assertEquals(2, jsonArray.size());
		Assertions.assertSame(foo, jsonArray.get(0));
		Assertions.assertSame(qux, jsonArray.get(1));
		Assertions.assertFalse(jsonElements.removeAll(ImmutableList.of(bar, baz)));
	}

	@Test
	public void testAsImmutableListCannotBeModifiedViaRetainAll() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonArray jsonArray = JsonArrays.of(foo, bar, baz, qux);
			final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
			jsonElements.retainAll(ImmutableList.of(bar, baz));
		});
	}

	@Test
	public void testAsMutableListCanBeModifiedViaRetainAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz, qux);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		Assertions.assertTrue(jsonElements.retainAll(ImmutableList.of(bar, baz)));
		Assertions.assertEquals(2, jsonArray.size());
		Assertions.assertSame(bar, jsonArray.get(0));
		Assertions.assertSame(baz, jsonArray.get(1));
		Assertions.assertFalse(jsonElements.retainAll(ImmutableList.of(bar, baz)));
		Assertions.assertEquals(2, jsonArray.size());
		Assertions.assertSame(bar, jsonArray.get(0));
		Assertions.assertSame(baz, jsonArray.get(1));
	}

	@Test
	public void testAsImmutableListCannotBeModifiedViaSet() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final JsonArray jsonArray = JsonArrays.of(bar, bar, baz, qux);
			final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
			jsonElements.set(0, foo);
		});
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
		final JsonArray array = new JsonArray(values.length);
		for ( final String value : values ) {
			array.add(value);
		}
		return array;
	}

}
