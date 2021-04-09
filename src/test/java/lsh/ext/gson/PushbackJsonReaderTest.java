package lsh.ext.gson;

import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Stream;

import com.google.common.collect.Iterators;
import com.google.gson.stream.JsonToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class PushbackJsonReaderTest {

	@Test
	public void testBeginArray()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[")) ) {
			unit.beginArray();
			assertPushback(unit, ValuedJsonToken.arrayBegin());
			unit.pushback();
			unit.beginArray();
			assertPushback(unit);
		}
	}

	@Test
	public void testEndArray()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[]")) ) {
			unit.beginArray();
			unit.endArray();
			assertPushback(unit, ValuedJsonToken.arrayBegin(), ValuedJsonToken.arrayEnd());
			unit.pushback();
			unit.beginArray();
			unit.endArray();
			assertPushback(unit);
		}
	}

	@Test
	public void testBeginObject()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("{")) ) {
			unit.beginObject();
			assertPushback(unit, ValuedJsonToken.objectBegin());
			unit.pushback();
			unit.beginObject();
			assertPushback(unit);
		}
	}

	@Test
	public void testEndObject()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("{}")) ) {
			unit.beginObject();
			unit.endObject();
			assertPushback(unit, ValuedJsonToken.objectBegin(), ValuedJsonToken.objectEnd());
			unit.pushback();
			unit.beginObject();
			unit.endObject();
			assertPushback(unit);
		}
	}

	@Test
	public void testHasNextForArrays()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[0]")) ) {
			unit.beginArray();
			Assertions.assertTrue(unit.hasNext());
			Assertions.assertEquals(0, unit.nextInt());
			Assertions.assertFalse(unit.hasNext());
			unit.endArray();
			assertPushback(unit, ValuedJsonToken.arrayBegin(), ValuedJsonToken.value(0), ValuedJsonToken.arrayEnd());
			unit.pushback();
			Assertions.assertTrue(unit.hasNext());
			unit.beginArray();
			Assertions.assertTrue(unit.hasNext());
			Assertions.assertEquals(0, unit.nextInt());
			Assertions.assertFalse(unit.hasNext());
			unit.endArray();
			assertPushback(unit);
		}
	}

	@Test
	public void testHasNextForEmptyArrays()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[]")) ) {
			unit.beginArray();
			Assertions.assertFalse(unit.hasNext());
			unit.endArray();
			assertPushback(unit, ValuedJsonToken.arrayBegin(), ValuedJsonToken.arrayEnd());
			unit.pushback();
			unit.beginArray();
			Assertions.assertFalse(unit.hasNext());
			unit.endArray();
			assertPushback(unit);
		}
	}

	@Test
	public void testHasNextForObjects()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("{\"key\":\"value\"}")) ) {
			unit.beginObject();
			Assertions.assertTrue(unit.hasNext());
			Assertions.assertEquals("key", unit.nextName());
			Assertions.assertTrue(unit.hasNext());
			Assertions.assertEquals("value", unit.nextString());
			Assertions.assertFalse(unit.hasNext());
			unit.endObject();
			assertPushback(unit, ValuedJsonToken.objectBegin(), ValuedJsonToken.name("key"), ValuedJsonToken.value("value"), ValuedJsonToken.objectEnd());
			unit.pushback();
			unit.beginObject();
			Assertions.assertTrue(unit.hasNext());
			Assertions.assertEquals("key", unit.nextName());
			Assertions.assertTrue(unit.hasNext());
			Assertions.assertEquals("value", unit.nextString());
			Assertions.assertFalse(unit.hasNext());
			unit.endObject();
			assertPushback(unit);
		}
	}

	@Test
	public void testHasNextForEmptyObjects()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("{}")) ) {
			unit.beginObject();
			Assertions.assertFalse(unit.hasNext());
			unit.endObject();
			assertPushback(unit, ValuedJsonToken.objectBegin(), ValuedJsonToken.objectEnd());
			unit.pushback();
			unit.beginObject();
			Assertions.assertFalse(unit.hasNext());
			unit.endObject();
			assertPushback(unit);
		}
	}

	@Test
	public void testHasNextForNestedArrays()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[[]]")) ) {
			unit.beginArray();
			Assertions.assertTrue(unit.hasNext());
			unit.beginArray();
			Assertions.assertFalse(unit.hasNext());
			unit.endArray();
			Assertions.assertFalse(unit.hasNext());
			unit.endArray();
			unit.pushback();
			assertPushback(unit, ValuedJsonToken.arrayBegin(), ValuedJsonToken.arrayBegin(), ValuedJsonToken.arrayEnd(), ValuedJsonToken.arrayEnd());
			unit.beginArray();
			Assertions.assertTrue(unit.hasNext());
			unit.beginArray();
			Assertions.assertFalse(unit.hasNext());
			unit.endArray();
			Assertions.assertFalse(unit.hasNext());
			unit.endArray();
			assertPushback(unit);
		}
	}

	@Test
	public void testPeek()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[]")) ) {
			Assertions.assertEquals(JsonToken.BEGIN_ARRAY, unit.peek());
			unit.beginArray();
			Assertions.assertEquals(JsonToken.END_ARRAY, unit.peek());
			unit.endArray();
			Assertions.assertEquals(JsonToken.END_DOCUMENT, unit.peek());
			unit.pushback();
			Assertions.assertEquals(JsonToken.BEGIN_ARRAY, unit.peek());
			unit.beginArray();
			Assertions.assertEquals(JsonToken.END_ARRAY, unit.peek());
			unit.endArray();
			Assertions.assertEquals(JsonToken.END_DOCUMENT, unit.peek());
		}
	}

	@Test
	public void testNextName()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("{\"key\":\"value\"}")) ) {
			unit.beginObject();
			Assertions.assertEquals("key", unit.nextName());
			Assertions.assertEquals("value", unit.nextString());
			unit.endObject();
			assertPushback(unit, ValuedJsonToken.objectBegin(), ValuedJsonToken.name("key"), ValuedJsonToken.value("value"), ValuedJsonToken.objectEnd());
			unit.pushback();
			unit.beginObject();
			Assertions.assertEquals("key", unit.nextName());
			Assertions.assertEquals("value", unit.nextString());
			unit.endObject();
			assertPushback(unit);
		}
	}

	@Test
	public void testNextString()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("\"value\"")) ) {
			Assertions.assertEquals("value", unit.nextString());
			assertPushback(unit, ValuedJsonToken.value("value"));
			unit.pushback();
			Assertions.assertEquals("value", unit.nextString());
			assertPushback(unit);
		}
	}

	@Test
	public void testNextBoolean()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("true")) ) {
			Assertions.assertTrue(unit.nextBoolean());
			assertPushback(unit, ValuedJsonToken.value(true));
			unit.pushback();
			Assertions.assertTrue(unit.nextBoolean());
			assertPushback(unit);
		}
	}

	@Test
	public void testNextNull()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("null")) ) {
			unit.nextNull();
			assertPushback(unit, ValuedJsonToken.value());
			unit.pushback();
			unit.nextNull();
			assertPushback(unit);
		}
	}

	@Test
	public void testNextDouble()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("3.1415926")) ) {
			Assertions.assertEquals(3.1415926D, unit.nextDouble());
			assertPushback(unit, ValuedJsonToken.value(3.1415926D));
			unit.pushback();
			Assertions.assertEquals(3.1415926D, unit.nextDouble());
			assertPushback(unit);
		}
	}

	@Test
	public void testNextDoubleFromString()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("\"3.1415926\"")) ) {
			Assertions.assertEquals("3.1415926", unit.nextString());
			assertPushback(unit, ValuedJsonToken.value("3.1415926"));
			unit.pushback();
			Assertions.assertEquals(3.1415926D, unit.nextDouble());
			assertPushback(unit);
		}
	}

	@Test
	public void testNextLong()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("31415926")) ) {
			Assertions.assertEquals(31415926L, unit.nextLong());
			assertPushback(unit, ValuedJsonToken.value(31415926L));
			unit.pushback();
			Assertions.assertEquals(31415926L, unit.nextLong());
			assertPushback(unit);
		}
	}

	@Test
	public void testNextLongFromString()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("\"31415926\"")) ) {
			Assertions.assertEquals("31415926", unit.nextString());
			assertPushback(unit, ValuedJsonToken.value("31415926"));
			unit.pushback();
			Assertions.assertEquals(31415926L, unit.nextLong());
			assertPushback(unit);
		}
	}

	@Test
	public void testNextInt()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("31415926")) ) {
			Assertions.assertEquals(31415926, unit.nextInt());
			assertPushback(unit, ValuedJsonToken.value(31415926));
			unit.pushback();
			Assertions.assertEquals(31415926, unit.nextInt());
			assertPushback(unit);
		}
	}

	@Test
	public void textNextIntFromString()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("\"31415926\"")) ) {
			Assertions.assertEquals("31415926", unit.nextString());
			assertPushback(unit, ValuedJsonToken.value("31415926"));
			unit.pushback();
			Assertions.assertEquals(31415926, unit.nextInt());
			assertPushback(unit);
		}
	}

	@Test
	public void testClose()
			throws IOException {
		@SuppressWarnings("resource")
		final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[0]"));
		try {
			unit.beginArray();
			unit.nextInt();
			unit.endArray();
			assertPushback(unit, ValuedJsonToken.arrayBegin(), ValuedJsonToken.value(0), ValuedJsonToken.arrayEnd());
		} finally {
			unit.close();
			assertPushback(unit);
		}
	}

	@Test
	public void testSkipValue()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[0,1,2,[8],[1000,2000,3000]]")) ) {
			unit.beginArray();
			unit.skipValue();
			unit.skipValue();
			unit.skipValue();
			unit.skipValue();
			unit.skipValue();
			unit.endArray();
			assertPushback(unit, ValuedJsonToken.arrayBegin(), ValuedJsonToken.value(0D), ValuedJsonToken.value(1D), ValuedJsonToken.value(2D), ValuedJsonToken.arrayBegin(), ValuedJsonToken.value(8D), ValuedJsonToken.arrayEnd(), ValuedJsonToken.arrayBegin(), ValuedJsonToken.value(1000D), ValuedJsonToken.value(2000D), ValuedJsonToken.value(3000D), ValuedJsonToken.arrayEnd(), ValuedJsonToken.arrayEnd());
			unit.pushback();
			unit.beginArray();
			Assertions.assertEquals(0, unit.nextInt());
			Assertions.assertEquals(1, unit.nextInt());
			Assertions.assertEquals(2, unit.nextInt());
			unit.skipValue();
			unit.beginArray();
			Assertions.assertEquals(1000, unit.nextInt());
			Assertions.assertEquals(2000, unit.nextInt());
			Assertions.assertEquals(3000, unit.nextInt());
			unit.endArray();
			unit.endArray();
			assertPushback(unit);
		}
	}

	@Test
	public void testToStringForRecordingIsNotImplementedYet() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[]")) ) {
				@SuppressWarnings("unused")
				final String s = unit.toString();
			}
		});
	}

	@Test
	public void testToStringForReplayingIsNotImplementedYet() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[]")) ) {
				unit.pushback();
				@SuppressWarnings("unused")
				final String s = unit.toString();
			}
		});
	}

	@Test
	public void testToStringForReplayed()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[1,2,3]")) ) {
			unit.beginArray();
			unit.nextInt();
			unit.nextInt();
			unit.pushback();
			unit.beginArray();
			unit.nextInt();
			unit.nextInt();
			Assertions.assertEquals("PushbackJsonReader at line 1 column 5 path $[2]", unit.toString());
		}
	}

	@Test
	public void testGetPathForRecordingIsNotImplementedYet() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[]")) ) {
				unit.getPath();
			}
		});
	}

	@Test
	public void testGetPathForReplayingIsNotImplementedYet() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[]")) ) {
				unit.pushback();
				unit.getPath();
			}
		});
	}

	@Test
	public void testGetPathForReplayed()
			throws IOException {
		try ( final PushbackJsonReader unit = PushbackJsonReader.getAndRecord(new StringReader("[1,2,3]")) ) {
			unit.beginArray();
			unit.nextInt();
			unit.nextInt();
			unit.pushback();
			unit.beginArray();
			unit.nextInt();
			unit.nextInt();
			Assertions.assertEquals("$[2]", unit.getPath());
		}
	}

	private static void assertPushback(final PushbackJsonReader pushbackJsonReader, final ValuedJsonToken<?>... tokens) {
		Iterators.elementsEqual(Stream.of(tokens).iterator(), pushbackJsonReader.iterator());
	}

}
