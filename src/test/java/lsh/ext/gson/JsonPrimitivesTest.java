package lsh.ext.gson;

import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class JsonPrimitivesTest {

	private static final String K1 = "foo";
	private static final String K2 = "bar";

	@Test
	public void testJsonPrimitiveForBooleans() {
		Assertions.assertEquals(new JsonPrimitive(true), JsonPrimitives.of(Boolean.TRUE));
		Assertions.assertEquals(new JsonPrimitive(false), JsonPrimitives.of(Boolean.FALSE));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitiveForBooleansAsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonPrimitives.of((Boolean) null));
	}

	@Test
	public void testJsonPrimitiveForNumbers() {
		Assertions.assertEquals(new JsonPrimitive(1), JsonPrimitives.of((byte) 1));
		Assertions.assertEquals(new JsonPrimitive(2), JsonPrimitives.of((short) 2));
		Assertions.assertEquals(new JsonPrimitive(3), JsonPrimitives.of(3));
		Assertions.assertEquals(new JsonPrimitive(4), JsonPrimitives.of(4L));
		Assertions.assertEquals(new JsonPrimitive(5), JsonPrimitives.of(5F));
		Assertions.assertEquals(new JsonPrimitive(6), JsonPrimitives.of(6D));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitiveForNumbersAsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonPrimitives.of((Number) null));
	}

	@Test
	public void testJsonPrimitivesForStrings() {
		Assertions.assertEquals(new JsonPrimitive(K1), JsonPrimitives.of(K1));
		Assertions.assertEquals(new JsonPrimitive(K2), JsonPrimitives.of(K2));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitivesForStringsAsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonPrimitives.of((String) null));
	}

	@Test
	public void testJsonPrimitivesForCharacters() {
		Assertions.assertEquals(new JsonPrimitive('f'), JsonPrimitives.of('f'));
		Assertions.assertEquals(new JsonPrimitive('b'), JsonPrimitives.of('b'));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitivesForCharactersAsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonPrimitives.of((Character) null));
	}

}
