package lsh.ext.gson;

import com.google.gson.JsonPrimitive;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class JsonPrimitivesTest {

	private static final String K1 = "foo";
	private static final String K2 = "bar";

	@Test
	public void testJsonPrimitiveForBooleans() {
		MatcherAssert.assertThat(JsonPrimitives.of(Boolean.TRUE), CoreMatchers.is(new JsonPrimitive(true)));
		MatcherAssert.assertThat(JsonPrimitives.of(Boolean.FALSE), CoreMatchers.is(new JsonPrimitive(false)));
	}

	@Test
	public void testJsonPrimitiveForBooleansIsFlyweight() {
		MatcherAssert.assertThat(JsonPrimitives.of(true), CoreMatchers.sameInstance(JsonPrimitives.of(Boolean.TRUE)));
		MatcherAssert.assertThat(JsonPrimitives.of(Boolean.FALSE), CoreMatchers.sameInstance(JsonPrimitives.of(false)));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitiveForBooleansAsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonPrimitives.of((Boolean) null));
	}

	@Test
	public void testJsonPrimitiveForNumbers() {
		MatcherAssert.assertThat(JsonPrimitives.of((byte) 1), CoreMatchers.is(new JsonPrimitive(1)));
		MatcherAssert.assertThat(JsonPrimitives.of((short) 2), CoreMatchers.is(new JsonPrimitive(2)));
		MatcherAssert.assertThat(JsonPrimitives.of(3), CoreMatchers.is(new JsonPrimitive(3)));
		MatcherAssert.assertThat(JsonPrimitives.of(4L), CoreMatchers.is(new JsonPrimitive(4)));
		MatcherAssert.assertThat(JsonPrimitives.of(5F), CoreMatchers.is(new JsonPrimitive(5)));
		MatcherAssert.assertThat(JsonPrimitives.of(6D), CoreMatchers.is(new JsonPrimitive(6)));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitiveForNumbersAsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonPrimitives.of((Number) null));
	}

	@Test
	public void testJsonPrimitivesForStrings() {
		MatcherAssert.assertThat(JsonPrimitives.of(K1), CoreMatchers.is(new JsonPrimitive(K1)));
		MatcherAssert.assertThat(JsonPrimitives.of(K2), CoreMatchers.is(new JsonPrimitive(K2)));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitivesForStringsAsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonPrimitives.of((String) null));
	}

	@Test
	public void testJsonPrimitivesForCharacters() {
		MatcherAssert.assertThat(JsonPrimitives.of('f'), CoreMatchers.is(new JsonPrimitive('f')));
		MatcherAssert.assertThat(JsonPrimitives.of('b'), CoreMatchers.is(new JsonPrimitive('b')));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitivesForCharactersAsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonPrimitives.of((Character) null));
	}

}
