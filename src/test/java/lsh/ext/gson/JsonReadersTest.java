package lsh.ext.gson;

import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import org.junit.Test;

import static com.google.gson.stream.JsonToken.BEGIN_ARRAY;
import static com.google.gson.stream.JsonToken.BEGIN_OBJECT;
import static com.google.gson.stream.JsonToken.END_DOCUMENT;
import static lsh.ext.gson.JsonReaders.getEmptyStringFailFastJsonReader;
import static lsh.ext.gson.JsonReaders.skipToken;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class JsonReadersTest {

	private static final Gson gson = new Gson();

	@Test
	public void testGsonWithoutEmptyStringFailFastJsonReaderMustNotFailOnReadingAnEmptyString() {
		gson.fromJson(new StringReader(""), Void.class);
	}

	@Test(expected = JsonSyntaxException.class)
	public void testGsonWithEmptyStringFailFastJsonReaderMustFailOnReadingAnEmptyString() {
		gson.fromJson(getEmptyStringFailFastJsonReader(new StringReader("")), Void.class);
	}

	@Test
	public void testSkipTokenForBeginArray()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[]"));
		assertThat(jsonReader.peek(), is(BEGIN_ARRAY));
		skipToken(jsonReader);
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForEndArray()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[]"));
		jsonReader.beginArray();
		skipToken(jsonReader);
		assertThat(jsonReader.peek(), is(END_DOCUMENT));
	}

	@Test
	public void testSkipTokenForBeginObject()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("{}"));
		assertThat(jsonReader.peek(), is(BEGIN_OBJECT));
		skipToken(jsonReader);
		jsonReader.endObject();
	}

	@Test
	public void testSkipTokenForEndObject()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("{}"));
		jsonReader.beginObject();
		skipToken(jsonReader);
		assertThat(jsonReader.peek(), is(END_DOCUMENT));
	}

	@Test
	public void testSkipTokenForName()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("{\"foo\":1}"));
		jsonReader.beginObject();
		skipToken(jsonReader);
		assertThat(jsonReader.nextInt(), is(1));
		jsonReader.endObject();
	}

	@Test
	public void testSkipTokenForString()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[\"foo\",\"bar\",\"baz\"]"));
		jsonReader.beginArray();
		assertThat(jsonReader.nextString(), is("foo"));
		skipToken(jsonReader);
		assertThat(jsonReader.nextString(), is("baz"));
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForNumber()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[1,2,3]"));
		jsonReader.beginArray();
		assertThat(jsonReader.nextInt(), is(1));
		skipToken(jsonReader);
		assertThat(jsonReader.nextInt(), is(3));
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForBoolean()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[false,true,true]"));
		jsonReader.beginArray();
		assertThat(jsonReader.nextBoolean(), is(false));
		skipToken(jsonReader);
		assertThat(jsonReader.nextBoolean(), is(true));
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForNull()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[null,true]"));
		jsonReader.beginArray();
		skipToken(jsonReader);
		assertThat(jsonReader.nextBoolean(), is(true));
		jsonReader.endArray();
	}

	@Test(expected = EOFException.class)
	public void testSkipTokenForEndOfDocument()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader(""));
		skipToken(jsonReader);
	}

}
