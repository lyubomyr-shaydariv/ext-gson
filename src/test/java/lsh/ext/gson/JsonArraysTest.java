package lsh.ext.gson;

import java.util.List;
import java.util.stream.Stream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class JsonArraysTest {

	private static final String[] es = {
			"foo", "bar", "baz", "qux", "quux"
	};

	private static final JsonPrimitive[] primitives = Stream.of(es)
			.map(JsonPrimitive::new)
			.toArray(JsonPrimitive[]::new);

	@Test
	public void testJsonArray() {
		Assertions.assertEquals(
				generateJsonArray(0),
				JsonArrays.of()
		);
	}

	@Test
	public void testJsonArray1() {
		Assertions.assertEquals(
				generateJsonArray(1),
				JsonArrays.of(primitives[0])
		);
		Assertions.assertEquals(
				generateNullJsonArray(1),
				JsonArrays.of((JsonElement) null)
		);
	}

	@Test
	public void testJsonArray2() {
		Assertions.assertEquals(
				generateJsonArray(2),
				JsonArrays.of(primitives[0], primitives[1])
		);
		Assertions.assertEquals(
				generateNullJsonArray(2),
				JsonArrays.of(null, null)
		);
	}

	@Test
	public void testJsonArray3() {
		Assertions.assertEquals(
				generateJsonArray(3),
				JsonArrays.of(primitives[0], primitives[1], primitives[2])
		);
		Assertions.assertEquals(
				generateNullJsonArray(3),
				JsonArrays.of(null, null, null)
		);
	}

	@Test
	public void testJsonArray4() {
		Assertions.assertEquals(
				generateJsonArray(4),
				JsonArrays.of(primitives[0], primitives[1], primitives[2], primitives[3])
		);
		Assertions.assertEquals(
				generateNullJsonArray(4),
				JsonArrays.of(null, null, null, null)
		);
	}

	@Test
	public void testFromIterable() {
		final List<JsonPrimitive> list = List.of(primitives[0], primitives[1], primitives[2]);
		final JsonArray jsonArray = JsonArrays.from(list);
		Assertions.assertEquals(list.size(), jsonArray.size());
		Assertions.assertSame(list.get(0), jsonArray.get(0));
		Assertions.assertSame(list.get(1), jsonArray.get(1));
		Assertions.assertSame(list.get(2), jsonArray.get(2));
	}

	private static JsonArray generateNullJsonArray(final int length) {
		final JsonArray jsonArray = new JsonArray(length);
		for ( int i = 0; i < length; i++ ) {
			jsonArray.add((String) null);
		}
		return jsonArray;
	}

	private static JsonArray generateJsonArray(final int length) {
		final JsonArray jsonArray = new JsonArray(length);
		for ( int i = 0; i < length && i < es.length; i++ ) {
			jsonArray.add(new JsonPrimitive(es[i]));
		}
		return jsonArray;
	}

}
