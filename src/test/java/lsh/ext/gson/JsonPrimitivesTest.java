package lsh.ext.gson;

import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class JsonPrimitivesTest {

	private static final String K1 = "foo";
	private static final String K2 = "bar";

	@Test
	public void testJsonPrimitiveForBooleans() {
		Assertions.assertEquals(new JsonPrimitive(true), JsonPrimitives.create(Boolean.TRUE));
		Assertions.assertEquals(new JsonPrimitive(false), JsonPrimitives.create(Boolean.FALSE));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitiveForBooleansAsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonPrimitives.create((Boolean) null));
	}

	@Test
	public void testJsonPrimitiveForNumbers() {
		Assertions.assertEquals(new JsonPrimitive(1), JsonPrimitives.create((byte) 1));
		Assertions.assertEquals(new JsonPrimitive(2), JsonPrimitives.create((short) 2));
		Assertions.assertEquals(new JsonPrimitive(3), JsonPrimitives.create(3));
		Assertions.assertEquals(new JsonPrimitive(4), JsonPrimitives.create(4L));
		Assertions.assertEquals(new JsonPrimitive(5), JsonPrimitives.create(5F));
		Assertions.assertEquals(new JsonPrimitive(6), JsonPrimitives.create(6D));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitiveForNumbersAsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonPrimitives.create((Number) null));
	}

	@Test
	public void testJsonPrimitivesForStrings() {
		Assertions.assertEquals(new JsonPrimitive(K1), JsonPrimitives.create(K1));
		Assertions.assertEquals(new JsonPrimitive(K2), JsonPrimitives.create(K2));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitivesForStringsAsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonPrimitives.create((String) null));
	}

	@Test
	public void testJsonPrimitivesForCharacters() {
		Assertions.assertEquals(new JsonPrimitive('f'), JsonPrimitives.create('f'));
		Assertions.assertEquals(new JsonPrimitive('b'), JsonPrimitives.create('b'));
	}

	@Test
	@SuppressWarnings("ConstantConditions")
	public void testJsonPrimitivesForCharactersAsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> JsonPrimitives.create((Character) null));
	}

}
