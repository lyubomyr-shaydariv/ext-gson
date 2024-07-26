package lsh.ext.gson;

import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class JsonReadersTest {

	private static final Gson gson = Gsons.getNormalized();

	@Test
	public void testIsJsonValid()
			throws IOException {
		Assertions.assertTrue(JsonReaders.isValid(new JsonReader(new StringReader("{\"foo\": \"bar\"}"))));
		Assertions.assertFalse(JsonReaders.isValid(new JsonReader(new StringReader("foobar"))));
	}

	@Test
	public void testGsonWithoutEmptyStringFailFastJsonReaderMustNotFailOnReadingAnEmptyString() {
		gson.fromJson(new StringReader(""), Void.class);
	}

	@Test
	public void testSkipTokenForBeginArray()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[]"));
		Assertions.assertEquals(JsonToken.BEGIN_ARRAY, jsonReader.peek());
		JsonReaders.skipToken(jsonReader);
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForEndArray()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[]"));
		jsonReader.beginArray();
		JsonReaders.skipToken(jsonReader);
		Assertions.assertEquals(JsonToken.END_DOCUMENT, jsonReader.peek());
	}

	@Test
	public void testSkipTokenForBeginObject()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("{}"));
		Assertions.assertEquals(JsonToken.BEGIN_OBJECT, jsonReader.peek());
		JsonReaders.skipToken(jsonReader);
		jsonReader.endObject();
	}

	@Test
	public void testSkipTokenForEndObject()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("{}"));
		jsonReader.beginObject();
		JsonReaders.skipToken(jsonReader);
		Assertions.assertEquals(JsonToken.END_DOCUMENT, jsonReader.peek());
	}

	@Test
	public void testSkipTokenForName()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("{\"foo\":1}"));
		jsonReader.beginObject();
		JsonReaders.skipToken(jsonReader);
		Assertions.assertEquals(1, jsonReader.nextInt());
		jsonReader.endObject();
	}

	@Test
	public void testSkipTokenForString()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[\"foo\",\"bar\",\"baz\"]"));
		jsonReader.beginArray();
		Assertions.assertEquals("foo", jsonReader.nextString());
		JsonReaders.skipToken(jsonReader);
		Assertions.assertEquals("baz", jsonReader.nextString());
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForNumber()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[1,2,3]"));
		jsonReader.beginArray();
		Assertions.assertEquals(1, jsonReader.nextInt());
		JsonReaders.skipToken(jsonReader);
		Assertions.assertEquals(3, jsonReader.nextInt());
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForBoolean()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[false,true,true]"));
		jsonReader.beginArray();
		Assertions.assertFalse(jsonReader.nextBoolean());
		JsonReaders.skipToken(jsonReader);
		Assertions.assertTrue(jsonReader.nextBoolean());
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForNull()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[null,true]"));
		jsonReader.beginArray();
		JsonReaders.skipToken(jsonReader);
		Assertions.assertTrue(jsonReader.nextBoolean());
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForEndOfDocument()
			throws IOException {
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("")) ) {
			Assertions.assertThrows(EOFException.class, () -> JsonReaders.skipToken(jsonReader));
		}
	}

	@Test
	public void testAsIteratorSome()
			throws IOException {
		final JsonReader in = new JsonReader(new StringReader("[{\"foo\":1,\"bar\":2},{\"foo\":3,\"bar\":4},{\"foo\":5,\"bar\":6}]"));
		in.beginArray();
		final Iterator<?> unit = JsonReaders.asIterator(in, gson.getAdapter(FooBar.class));
		Assertions.assertTrue(unit.hasNext());
		Assertions.assertEquals(new FooBar(1, 2), unit.next());
		Assertions.assertTrue(unit.hasNext());
		Assertions.assertEquals(new FooBar(3, 4), unit.next());
		Assertions.assertTrue(unit.hasNext());
		Assertions.assertEquals(new FooBar(5, 6), unit.next());
		Assertions.assertFalse(unit.hasNext());
		Assertions.assertThrows(NoSuchElementException.class, unit::next);
		in.endArray();
	}

	@Test
	public void testAsIteratorEmpty()
			throws IOException {
		final JsonReader in = new JsonReader(new StringReader("[]"));
		in.beginArray();
		final Iterator<?> unit = JsonReaders.asIterator(in, gson.getAdapter(FooBar.class));
		Assertions.assertFalse(unit.hasNext());
		Assertions.assertThrows(NoSuchElementException.class, unit::next);
		in.endArray();
	}

	private record FooBar(
			int foo,
			int bar
	) {
	}

}
