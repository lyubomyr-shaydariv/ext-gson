package com.google.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class TypeAdapterTest {

	@Test
	public void testTypeAdapterNullSafe() {
		final TypeAdapter<?> unit = typeAdapter;
		final TypeAdapter<?> nullSafeUnit1 = unit.nullSafe();
		final TypeAdapter<?> nullSafeUnit2 = unit.nullSafe();
		Assertions.assertNotSame(nullSafeUnit1, nullSafeUnit2);
		Assertions.assertSame(nullSafeUnit1, nullSafeUnit2.nullSafe(), () -> String.format("Must pass the test once %s is resolved", "https://github.com/google/gson/issues/2729"));
		Assertions.assertSame(nullSafeUnit1.nullSafe(), nullSafeUnit2, () -> String.format("Must pass the test once %s is resolved", "https://github.com/google/gson/issues/2729"));
	}

	private static final TypeAdapter<Object> typeAdapter = new TypeAdapter<>() {
		@Override
		public void write(final JsonWriter out, final Object value) {
			throw new AssertionError();
		}

		@Override
		public Object read(final JsonReader in) {
			throw new AssertionError();
		}
	};

}
