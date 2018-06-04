package lsh.ext.gson;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
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
		MatcherAssert.assertThat(
				JsonArrays.of(),
				CoreMatchers.is(stringJsonArray())
		);
	}

	@Test
	public void testJsonArray1() {
		MatcherAssert.assertThat(
				JsonArrays.of(JsonPrimitives.of(K1)),
				CoreMatchers.is(stringJsonArray(K1))
		);
		MatcherAssert.assertThat(
				JsonArrays.of(null),
				CoreMatchers.is(stringJsonArray((String) null))
		);
	}

	@Test
	public void testJsonArray2() {
		MatcherAssert.assertThat(
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2)),
				CoreMatchers.is(stringJsonArray(K1, K2)));
		MatcherAssert.assertThat(
				JsonArrays.of(null, null),
				CoreMatchers.is(stringJsonArray(null, null))
		);
	}

	@Test
	public void testJsonArray3() {
		MatcherAssert.assertThat(
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3)));
		MatcherAssert.assertThat(
				JsonArrays.of(null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null))
		);
	}

	@Test
	public void testJsonArray4() {
		MatcherAssert.assertThat(
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4)));
		MatcherAssert.assertThat(
				JsonArrays.of(null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null))
		);
	}

	@Test
	public void testJsonArray5() {
		MatcherAssert.assertThat(
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5)));
		MatcherAssert.assertThat(
				JsonArrays.of(null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray6() {
		MatcherAssert.assertThat(
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6)));
		MatcherAssert.assertThat(
				JsonArrays.of(null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray7() {
		MatcherAssert.assertThat(
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6), JsonPrimitives.of(K7)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7)));
		MatcherAssert.assertThat(
				JsonArrays.of(null, null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray8() {
		MatcherAssert.assertThat(
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6), JsonPrimitives.of(K7), JsonPrimitives.of(K8)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8)));
		MatcherAssert.assertThat(
				JsonArrays.of(null, null, null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray9() {
		MatcherAssert.assertThat(
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6), JsonPrimitives.of(K7), JsonPrimitives.of(K8), JsonPrimitives.of(K9)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8, K9)));
		MatcherAssert.assertThat(
				JsonArrays.of(null, null, null, null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null, null, null, null))
		);
	}

	@Test
	public void testJsonArray10() {
		MatcherAssert.assertThat(
				JsonArrays.of(JsonPrimitives.of(K1), JsonPrimitives.of(K2), JsonPrimitives.of(K3), JsonPrimitives.of(K4), JsonPrimitives.of(K5), JsonPrimitives.of(K6), JsonPrimitives.of(K7), JsonPrimitives.of(K8), JsonPrimitives.of(K9), JsonPrimitives.of(K10)),
				CoreMatchers.is(stringJsonArray(K1, K2, K3, K4, K5, K6, K7, K8, K9, K10)));
		MatcherAssert.assertThat(
				JsonArrays.of(null, null, null, null, null, null, null, null, null, null),
				CoreMatchers.is(stringJsonArray(null, null, null, null, null, null, null, null, null, null))
		);
	}

	@Test
	public void testFromIterable() {
		final JsonPrimitive element1 = JsonPrimitives.of(K1);
		final JsonPrimitive element2 = JsonPrimitives.of(K2);
		final JsonPrimitive element3 = JsonPrimitives.of(K3);
		final Iterable<? extends JsonElement> jsonElements = ImmutableList.of(element1, element2, element3);
		final JsonArray jsonArray = JsonArrays.from(jsonElements);
		MatcherAssert.assertThat(jsonArray.size(), CoreMatchers.is(3));
		MatcherAssert.assertThat(jsonArray.get(0), CoreMatchers.sameInstance(element1));
		MatcherAssert.assertThat(jsonArray.get(1), CoreMatchers.sameInstance(element2));
		MatcherAssert.assertThat(jsonArray.get(2), CoreMatchers.sameInstance(element3));
	}

	@Test
	public void testFromCollection() {
		final JsonPrimitive element1 = JsonPrimitives.of(K1);
		final JsonPrimitive element2 = JsonPrimitives.of(K2);
		final JsonPrimitive element3 = JsonPrimitives.of(K3);
		final List<? extends JsonElement> jsonElements = ImmutableList.of(element1, element2, element3);
		final List<? extends JsonElement> spyJsonElements = Mockito.spy(jsonElements);
		final JsonArray jsonArray = JsonArrays.from(spyJsonElements);
		MatcherAssert.assertThat(jsonArray.size(), CoreMatchers.is(3));
		MatcherAssert.assertThat(jsonArray.get(0), CoreMatchers.sameInstance(element1));
		MatcherAssert.assertThat(jsonArray.get(1), CoreMatchers.sameInstance(element2));
		MatcherAssert.assertThat(jsonArray.get(2), CoreMatchers.sameInstance(element3));
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
		MatcherAssert.assertThat(actual, CoreMatchers.is(expected));
	}

	@Test
	public void testAsImmutableList() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		MatcherAssert.assertThat(jsonElements.size(), CoreMatchers.is(3));
		MatcherAssert.assertThat(jsonElements.get(0), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonElements.get(1), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonElements.get(2), CoreMatchers.sameInstance(baz));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAsImmutableListCannotBeModifiedViaAdd() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		jsonElements.add(qux);
	}

	@Test
	public void testAsMutableListCanBeModifiedViaAdd() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		MatcherAssert.assertThat(jsonElements.size(), CoreMatchers.is(3));
		MatcherAssert.assertThat(jsonElements.get(0), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonElements.get(1), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonElements.get(2), CoreMatchers.sameInstance(baz));
		MatcherAssert.assertThat(jsonElements.add(qux), CoreMatchers.is(true));
		MatcherAssert.assertThat(jsonElements.size(), CoreMatchers.is(4));
		MatcherAssert.assertThat(jsonElements.get(0), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonElements.get(1), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonElements.get(2), CoreMatchers.sameInstance(baz));
		MatcherAssert.assertThat(jsonElements.get(3), CoreMatchers.sameInstance(qux));
		MatcherAssert.assertThat(jsonArray.size(), CoreMatchers.is(4));
		MatcherAssert.assertThat(jsonArray.get(0), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonArray.get(1), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonArray.get(2), CoreMatchers.sameInstance(baz));
		MatcherAssert.assertThat(jsonArray.get(3), CoreMatchers.sameInstance(qux));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAsImmutableListCannotBeModifiedViaAddByIndex() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		jsonElements.add(0, qux);
	}

	@Test
	public void testAsMutableListCanBeModifiedViaAddByIndex() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		MatcherAssert.assertThat(jsonElements.size(), CoreMatchers.is(3));
		MatcherAssert.assertThat(jsonElements.get(0), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonElements.get(1), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonElements.get(2), CoreMatchers.sameInstance(baz));
		jsonElements.add(0, qux);
		MatcherAssert.assertThat(jsonElements.size(), CoreMatchers.is(4));
		MatcherAssert.assertThat(jsonElements.get(0), CoreMatchers.sameInstance(qux));
		MatcherAssert.assertThat(jsonElements.get(1), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonElements.get(2), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonElements.get(3), CoreMatchers.sameInstance(baz));
		MatcherAssert.assertThat(jsonArray.size(), CoreMatchers.is(4));
		MatcherAssert.assertThat(jsonArray.get(0), CoreMatchers.sameInstance(qux));
		MatcherAssert.assertThat(jsonArray.get(1), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonArray.get(2), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonArray.get(3), CoreMatchers.sameInstance(baz));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAsImmutableListCannotBeModifiedViaAddAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		jsonElements.addAll(Collections.emptyList());
	}

	@Test
	public void testAsMutableListCanBeModifiedViaAddAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		MatcherAssert.assertThat(jsonElements.size(), CoreMatchers.is(2));
		MatcherAssert.assertThat(jsonElements.get(0), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonElements.get(1), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonElements.addAll(ImmutableList.of(baz, qux)), CoreMatchers.is(true));
		MatcherAssert.assertThat(jsonElements.size(), CoreMatchers.is(4));
		MatcherAssert.assertThat(jsonElements.get(0), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonElements.get(1), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonElements.get(2), CoreMatchers.sameInstance(baz));
		MatcherAssert.assertThat(jsonElements.get(3), CoreMatchers.sameInstance(qux));
		MatcherAssert.assertThat(jsonArray.size(), CoreMatchers.is(4));
		MatcherAssert.assertThat(jsonArray.get(0), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonArray.get(1), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonArray.get(2), CoreMatchers.sameInstance(baz));
		MatcherAssert.assertThat(jsonArray.get(3), CoreMatchers.sameInstance(qux));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAsImmutableListCannotBeModifiedViaAddAllViaIndex() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		jsonElements.addAll(0, Collections.emptyList());
	}

	@Test
	public void testAsMutableListCanBeModifiedViaAddAllViaIndex() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		MatcherAssert.assertThat(jsonElements.size(), CoreMatchers.is(2));
		MatcherAssert.assertThat(jsonElements.get(0), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonElements.get(1), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonElements.addAll(0, ImmutableList.of(baz, qux)), CoreMatchers.is(true));
		MatcherAssert.assertThat(jsonElements.size(), CoreMatchers.is(4));
		MatcherAssert.assertThat(jsonElements.get(0), CoreMatchers.sameInstance(baz));
		MatcherAssert.assertThat(jsonElements.get(1), CoreMatchers.sameInstance(qux));
		MatcherAssert.assertThat(jsonElements.get(2), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonElements.get(3), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonArray.size(), CoreMatchers.is(4));
		MatcherAssert.assertThat(jsonArray.get(0), CoreMatchers.sameInstance(baz));
		MatcherAssert.assertThat(jsonArray.get(1), CoreMatchers.sameInstance(qux));
		MatcherAssert.assertThat(jsonArray.get(2), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonArray.get(3), CoreMatchers.sameInstance(bar));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAsImmutableListCannotBeModifiedViaClear() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		jsonElements.clear();
	}

	@Test
	public void testAsMutableListCanBeModifiedViaClear() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		jsonElements.clear();
		MatcherAssert.assertThat(jsonArray.size(), CoreMatchers.is(0));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAsImmutableListCannotBeModifiedViaRemove() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		jsonElements.remove(foo);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAsImmutableListCannotBeModifiedViaRemoveEvenIfObjectIsNotJsonElement() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		jsonElements.remove("whatever");
	}

	@Test
	public void testAsMutableListCanBeModifiedViaRemove() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		MatcherAssert.assertThat(jsonElements.remove(foo), CoreMatchers.is(true));
		MatcherAssert.assertThat(jsonArray.size(), CoreMatchers.is(2));
		MatcherAssert.assertThat(jsonArray.get(0), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonArray.get(1), CoreMatchers.sameInstance(baz));
		MatcherAssert.assertThat(jsonElements.remove(qux), CoreMatchers.is(false));
		MatcherAssert.assertThat(jsonArray.size(), CoreMatchers.is(2));
		MatcherAssert.assertThat(jsonArray.get(0), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonArray.get(1), CoreMatchers.sameInstance(baz));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAsImmutableListCannotBeModifiedViaRemoveViaIndex() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		jsonElements.remove(1);
	}

	@Test
	public void testAsMutableListCanBeModifiedViaRemoveViaIndex() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		MatcherAssert.assertThat(jsonElements.remove(1), CoreMatchers.is(bar));
		MatcherAssert.assertThat(jsonArray.size(), CoreMatchers.is(2));
		MatcherAssert.assertThat(jsonArray.get(0), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonArray.get(1), CoreMatchers.sameInstance(baz));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAsImmutableListCannotBeModifiedViaRemoveAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz, qux);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		jsonElements.removeAll(ImmutableList.of(bar, baz));
	}

	@Test
	public void testAsMutableListCanBeModifiedViaRemoveAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz, qux);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		MatcherAssert.assertThat(jsonElements.removeAll(ImmutableList.of(bar, baz)), CoreMatchers.is(true));
		MatcherAssert.assertThat(jsonArray.size(), CoreMatchers.is(2));
		MatcherAssert.assertThat(jsonArray.get(0), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonArray.get(1), CoreMatchers.sameInstance(qux));
		MatcherAssert.assertThat(jsonElements.removeAll(ImmutableList.of(bar, baz)), CoreMatchers.is(false));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAsImmutableListCannotBeModifiedViaRetainAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz, qux);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		jsonElements.retainAll(ImmutableList.of(bar, baz));
	}

	@Test
	public void testAsMutableListCanBeModifiedViaRetainAll() {
		final JsonArray jsonArray = JsonArrays.of(foo, bar, baz, qux);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		MatcherAssert.assertThat(jsonElements.retainAll(ImmutableList.of(bar, baz)), CoreMatchers.is(true));
		MatcherAssert.assertThat(jsonArray.size(), CoreMatchers.is(2));
		MatcherAssert.assertThat(jsonArray.get(0), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonArray.get(1), CoreMatchers.sameInstance(baz));
		MatcherAssert.assertThat(jsonElements.retainAll(ImmutableList.of(bar, baz)), CoreMatchers.is(false));
		MatcherAssert.assertThat(jsonArray.size(), CoreMatchers.is(2));
		MatcherAssert.assertThat(jsonArray.get(0), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonArray.get(1), CoreMatchers.sameInstance(baz));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testAsImmutableListCannotBeModifiedViaSet() {
		final JsonArray jsonArray = JsonArrays.of(bar, bar, baz, qux);
		final List<JsonElement> jsonElements = JsonArrays.asImmutableList(jsonArray);
		jsonElements.set(0, foo);
	}

	@Test
	public void testAsMutableListCanBeModifiedViaSet() {
		final JsonArray jsonArray = JsonArrays.of(bar, bar, baz);
		final List<JsonElement> jsonElements = JsonArrays.asMutableList(jsonArray);
		MatcherAssert.assertThat(jsonElements.set(0, foo), CoreMatchers.is(bar));
		MatcherAssert.assertThat(jsonArray.size(), CoreMatchers.is(3));
		MatcherAssert.assertThat(jsonArray.get(0), CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(jsonArray.get(1), CoreMatchers.sameInstance(bar));
		MatcherAssert.assertThat(jsonArray.get(2), CoreMatchers.sameInstance(baz));
	}

	private static JsonArray stringJsonArray(final String... values) {
		final JsonArray array = new JsonArray();
		for ( final String value : values ) {
			array.add(value);
		}
		return array;
	}

}
