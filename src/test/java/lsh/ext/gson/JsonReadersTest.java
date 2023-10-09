package lsh.ext.gson;

import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;
import java.util.NoSuchElementException;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import lsh.ext.gson.ext.java.ICloseableIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class JsonReadersTest {

	private static final Gson gson = GsonBuilders.createNormalized()
			.create();

	@Test
	public void isJsonValid()
			throws IOException {
		Assertions.assertTrue(JsonReaders.isValid(new JsonReader(new StringReader("{\"foo\": \"bar\"}"))));
		Assertions.assertFalse(JsonReaders.isValid(new JsonReader(new StringReader("foobar"))));
	}

	@Test
	public void testGsonWithoutEmptyStringFailFastJsonReaderMustNotFailOnReadingAnEmptyString() {
		gson.fromJson(new StringReader(""), Void.class);
	}

	@Test
	public void testGsonWithEmptyStringFailFastJsonReaderMustFailOnReadingAnEmptyString() {
		Assertions.assertThrows(JsonSyntaxException.class, () -> gson.fromJson(JsonReaders.getEmptyStringFailFastJsonReader(new StringReader("")), Void.class));
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
	public void testSkipTokenForEndOfDocument() {
		Assertions.assertThrows(EOFException.class, () -> {
			final JsonReader jsonReader = new JsonReader(new StringReader(""));
			JsonReaders.skipToken(jsonReader);
		});
	}

	@Test
	public void testReadValuedJsonToken()
			throws IOException {
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("[]")) ) {
			assertSequence(jsonReader, ValuedJsonToken.arrayBegin(), ValuedJsonToken.arrayEnd(), ValuedJsonToken.documentEnd());
		}
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("{}")) ) {
			assertSequence(jsonReader, ValuedJsonToken.objectBegin(), ValuedJsonToken.objectEnd(), ValuedJsonToken.documentEnd());
		}
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("{\"string\":\"fifty\",\"number\":900,\"boolean\":true,\"\":null}")) ) {
			assertSequence(jsonReader, ValuedJsonToken.objectBegin(), ValuedJsonToken.name("string"), ValuedJsonToken.value("fifty"), ValuedJsonToken.name("number"), ValuedJsonToken.value(900D), ValuedJsonToken.name("boolean"), ValuedJsonToken.value(true), ValuedJsonToken.name(""), ValuedJsonToken.value(), ValuedJsonToken.objectEnd(), ValuedJsonToken.documentEnd());
		}
	}

	@Test
	public void testReadValuedJsonTokenRecursivelyForStrings()
			throws IOException {
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("\"foo\" \"bar\"")) ) {
			jsonReader.setLenient(true);
			@SuppressWarnings("resource")
			final ICloseableIterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(jsonReader);
			Assertions.assertEquals(ImmutableList.of(ValuedJsonToken.value("foo")), ImmutableList.copyOf(iterator));
		}
	}

	@Test
	public void testReadValuedJsonTokenRecursivelyForNumbers()
			throws IOException {
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("100 300")) ) {
			jsonReader.setLenient(true);
			@SuppressWarnings("resource")
			final ICloseableIterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(jsonReader);
			Assertions.assertEquals(ImmutableList.of(ValuedJsonToken.value(100D)), ImmutableList.copyOf(iterator));
		}
	}

	@Test
	public void testReadValuedJsonTokenRecursivelyForBooleans()
			throws IOException {
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("true false")) ) {
			jsonReader.setLenient(true);
			@SuppressWarnings("resource")
			final ICloseableIterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(jsonReader);
			Assertions.assertEquals(ImmutableList.of(ValuedJsonToken.value(true)), ImmutableList.copyOf(iterator));
		}
	}

	@Test
	public void testReadValuedJsonTokenRecursivelyForNulls()
			throws IOException {
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("null false")) ) {
			jsonReader.setLenient(true);
			@SuppressWarnings("resource")
			final ICloseableIterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(jsonReader);
			Assertions.assertEquals(ImmutableList.of(ValuedJsonToken.value()), ImmutableList.copyOf(iterator));
		}
	}

	@Test
	public void testReadValuedJsonTokenRecursivelyForArrays()
			throws IOException {
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("[1,2,3] false")) ) {
			jsonReader.setLenient(true);
			@SuppressWarnings("resource")
			final ICloseableIterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(jsonReader);
			Assertions.assertEquals(ImmutableList.of(ValuedJsonToken.arrayBegin(), ValuedJsonToken.value(1D), ValuedJsonToken.value(2D), ValuedJsonToken.value(3D), ValuedJsonToken.arrayEnd()), ImmutableList.copyOf(iterator));
		}
	}

	@Test
	public void testReadValuedJsonTokenRecursivelyForObjects()
			throws IOException {
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("{\"foo\":1,\"bar\":2} 300")) ) {
			jsonReader.setLenient(true);
			@SuppressWarnings("resource")
			final ICloseableIterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(jsonReader);
			Assertions.assertEquals(ImmutableList.of(ValuedJsonToken.objectBegin(), ValuedJsonToken.name("foo"), ValuedJsonToken.value(1D), ValuedJsonToken.name("bar"), ValuedJsonToken.value(2D), ValuedJsonToken.objectEnd()), ImmutableList.copyOf(iterator));
		}
	}

	@Test
	public void testReadValuedJsonTokenRecursivelyMustNotProvideAConsecutiveToken() {
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			try ( final JsonReader jsonReader = new JsonReader(new StringReader("100 300")) ) {
				jsonReader.setLenient(true);
				@SuppressWarnings("resource")
				final ICloseableIterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(jsonReader);
				Assertions.assertEquals(ImmutableList.of(ValuedJsonToken.value(100D)), ImmutableList.copyOf(iterator));
				iterator.next();
			}
		});
	}

	private static void assertSequence(final JsonReader jsonReader, final ValuedJsonToken<?>... tokens)
			throws IOException {
		for ( final ValuedJsonToken<?> expectedValuedJsonToken : tokens ) {
			Assertions.assertFalse(jsonReader.peek() == JsonToken.END_DOCUMENT && expectedValuedJsonToken.getToken() != JsonToken.END_DOCUMENT);
			final ValuedJsonToken<?> actualValuedJsonToken = JsonReaders.readValuedJsonToken(jsonReader);
			Assertions.assertEquals(expectedValuedJsonToken, actualValuedJsonToken);
		}
	}

}
