package lsh.ext.gson;

import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class JsonReadersTest {

	private static final Gson gson = new Gson();

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
		MatcherAssert.assertThat(jsonReader.peek(), CoreMatchers.is(JsonToken.BEGIN_ARRAY));
		JsonReaders.skipToken(jsonReader);
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForEndArray()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[]"));
		jsonReader.beginArray();
		JsonReaders.skipToken(jsonReader);
		MatcherAssert.assertThat(jsonReader.peek(), CoreMatchers.is(JsonToken.END_DOCUMENT));
	}

	@Test
	public void testSkipTokenForBeginObject()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("{}"));
		MatcherAssert.assertThat(jsonReader.peek(), CoreMatchers.is(JsonToken.BEGIN_OBJECT));
		JsonReaders.skipToken(jsonReader);
		jsonReader.endObject();
	}

	@Test
	public void testSkipTokenForEndObject()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("{}"));
		jsonReader.beginObject();
		JsonReaders.skipToken(jsonReader);
		MatcherAssert.assertThat(jsonReader.peek(), CoreMatchers.is(JsonToken.END_DOCUMENT));
	}

	@Test
	public void testSkipTokenForName()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("{\"foo\":1}"));
		jsonReader.beginObject();
		JsonReaders.skipToken(jsonReader);
		MatcherAssert.assertThat(jsonReader.nextInt(), CoreMatchers.is(1));
		jsonReader.endObject();
	}

	@Test
	public void testSkipTokenForString()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[\"foo\",\"bar\",\"baz\"]"));
		jsonReader.beginArray();
		MatcherAssert.assertThat(jsonReader.nextString(), CoreMatchers.is("foo"));
		JsonReaders.skipToken(jsonReader);
		MatcherAssert.assertThat(jsonReader.nextString(), CoreMatchers.is("baz"));
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForNumber()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[1,2,3]"));
		jsonReader.beginArray();
		MatcherAssert.assertThat(jsonReader.nextInt(), CoreMatchers.is(1));
		JsonReaders.skipToken(jsonReader);
		MatcherAssert.assertThat(jsonReader.nextInt(), CoreMatchers.is(3));
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForBoolean()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[false,true,true]"));
		jsonReader.beginArray();
		MatcherAssert.assertThat(jsonReader.nextBoolean(), CoreMatchers.is(false));
		JsonReaders.skipToken(jsonReader);
		MatcherAssert.assertThat(jsonReader.nextBoolean(), CoreMatchers.is(true));
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForNull()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[null,true]"));
		jsonReader.beginArray();
		JsonReaders.skipToken(jsonReader);
		MatcherAssert.assertThat(jsonReader.nextBoolean(), CoreMatchers.is(true));
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
			MatcherAssert.assertThat(jsonReader, readsSequence(ValuedJsonToken.arrayBegin(), ValuedJsonToken.arrayEnd(), ValuedJsonToken.documentEnd()));
		}
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("{}")) ) {
			MatcherAssert.assertThat(jsonReader, readsSequence(ValuedJsonToken.objectBegin(), ValuedJsonToken.objectEnd(), ValuedJsonToken.documentEnd()));
		}
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("{\"string\":\"fifty\",\"number\":900,\"boolean\":true,\"\":null}")) ) {
			MatcherAssert.assertThat(jsonReader, readsSequence(ValuedJsonToken.objectBegin(), ValuedJsonToken.name("string"), ValuedJsonToken.value("fifty"), ValuedJsonToken.name("number"), ValuedJsonToken.value(900D), ValuedJsonToken.name("boolean"), ValuedJsonToken.value(true), ValuedJsonToken.name(""), ValuedJsonToken.value(), ValuedJsonToken.objectEnd(), ValuedJsonToken.documentEnd()));
		}
	}

	@Test
	public void testReadValuedJsonTokenRecursivelyForStrings()
			throws IOException {
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("\"foo\" \"bar\"")) ) {
			jsonReader.setLenient(true);
			@SuppressWarnings("resource")
			final ICloseableIterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(jsonReader);
			MatcherAssert.assertThat(ImmutableList.copyOf(iterator), CoreMatchers.is(ImmutableList.of(ValuedJsonToken.value("foo"))));
		}
	}

	@Test
	public void testReadValuedJsonTokenRecursivelyForNumbers()
			throws IOException {
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("100 300")) ) {
			jsonReader.setLenient(true);
			@SuppressWarnings("resource")
			final ICloseableIterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(jsonReader);
			MatcherAssert.assertThat(ImmutableList.copyOf(iterator), CoreMatchers.is(ImmutableList.of(ValuedJsonToken.value(100D))));
		}
	}

	@Test
	public void testReadValuedJsonTokenRecursivelyForBooleans()
			throws IOException {
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("true false")) ) {
			jsonReader.setLenient(true);
			@SuppressWarnings("resource")
			final ICloseableIterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(jsonReader);
			MatcherAssert.assertThat(ImmutableList.copyOf(iterator), CoreMatchers.is(ImmutableList.of(ValuedJsonToken.value(true))));
		}
	}

	@Test
	public void testReadValuedJsonTokenRecursivelyForNulls()
			throws IOException {
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("null false")) ) {
			jsonReader.setLenient(true);
			@SuppressWarnings("resource")
			final ICloseableIterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(jsonReader);
			MatcherAssert.assertThat(ImmutableList.copyOf(iterator), CoreMatchers.is(ImmutableList.of(ValuedJsonToken.value())));
		}
	}

	@Test
	public void testReadValuedJsonTokenRecursivelyForArrays()
			throws IOException {
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("[1,2,3] false")) ) {
			jsonReader.setLenient(true);
			@SuppressWarnings("resource")
			final ICloseableIterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(jsonReader);
			MatcherAssert.assertThat(ImmutableList.copyOf(iterator), CoreMatchers.is(ImmutableList.of(ValuedJsonToken.arrayBegin(), ValuedJsonToken.value(1D), ValuedJsonToken.value(2D), ValuedJsonToken.value(3D), ValuedJsonToken.arrayEnd())));
		}
	}

	@Test
	public void testReadValuedJsonTokenRecursivelyForObjects()
			throws IOException {
		try ( final JsonReader jsonReader = new JsonReader(new StringReader("{\"foo\":1,\"bar\":2} 300")) ) {
			jsonReader.setLenient(true);
			@SuppressWarnings("resource")
			final ICloseableIterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(jsonReader);
			MatcherAssert.assertThat(ImmutableList.copyOf(iterator), CoreMatchers.is(ImmutableList.of(ValuedJsonToken.objectBegin(), ValuedJsonToken.name("foo"), ValuedJsonToken.value(1D), ValuedJsonToken.name("bar"), ValuedJsonToken.value(2D), ValuedJsonToken.objectEnd())));
		}
	}

	@Test
	public void testReadValuedJsonTokenRecursivelyMustNotProvideAConsecutiveToken() {
		Assertions.assertThrows(NoSuchElementException.class, () -> {
			try ( final JsonReader jsonReader = new JsonReader(new StringReader("100 300")) ) {
				jsonReader.setLenient(true);
				@SuppressWarnings("resource")
				final ICloseableIterator<ValuedJsonToken<?>> iterator = JsonReaders.readValuedJsonTokenRecursively(jsonReader);
				MatcherAssert.assertThat(ImmutableList.copyOf(iterator), CoreMatchers.is(ImmutableList.of(ValuedJsonToken.value(100D))));
				iterator.next();
			}
		});
	}

	private static Matcher<JsonReader> readsSequence(final ValuedJsonToken<?>... tokens) {
		final List<ValuedJsonToken<?>> tokensCopy = ImmutableList.copyOf(tokens);
		return new TypeSafeMatcher<JsonReader>() {
			@Override
			protected boolean matchesSafely(final JsonReader jsonReader) {
				try {
					for ( final ValuedJsonToken<?> expectedValuedJsonToken : tokensCopy ) {
						if ( jsonReader.peek() == JsonToken.END_DOCUMENT && expectedValuedJsonToken.getToken() != JsonToken.END_DOCUMENT ) {
							return false;
						}
						final ValuedJsonToken<?> actualValuedJsonToken = JsonReaders.readValuedJsonToken(jsonReader);
						if ( !expectedValuedJsonToken.equals(actualValuedJsonToken) ) {
							return false;
						}
					}
					return true;
				} catch ( final IOException ex ) {
					throw new RuntimeException(ex);
				}
			}

			@Override
			public void describeTo(final Description description) {
				description.appendValue(tokensCopy);
			}
		};
	}

}
