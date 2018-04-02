package lsh.ext.gson;

import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public final class JsonReadersTest {

	private static final Gson gson = new Gson();

	@Test
	public void testGsonWithoutEmptyStringFailFastJsonReaderMustNotFailOnReadingAnEmptyString() {
		gson.fromJson(new StringReader(""), Void.class);
	}

	@Test(expected = JsonSyntaxException.class)
	public void testGsonWithEmptyStringFailFastJsonReaderMustFailOnReadingAnEmptyString() {
		gson.fromJson(JsonReaders.getEmptyStringFailFastJsonReader(new StringReader("")), Void.class);
	}

	@Test
	public void testSkipTokenForBeginArray()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[]"));
		Assert.assertThat(jsonReader.peek(), CoreMatchers.is(JsonToken.BEGIN_ARRAY));
		JsonReaders.skipToken(jsonReader);
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForEndArray()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[]"));
		jsonReader.beginArray();
		JsonReaders.skipToken(jsonReader);
		Assert.assertThat(jsonReader.peek(), CoreMatchers.is(JsonToken.END_DOCUMENT));
	}

	@Test
	public void testSkipTokenForBeginObject()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("{}"));
		Assert.assertThat(jsonReader.peek(), CoreMatchers.is(JsonToken.BEGIN_OBJECT));
		JsonReaders.skipToken(jsonReader);
		jsonReader.endObject();
	}

	@Test
	public void testSkipTokenForEndObject()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("{}"));
		jsonReader.beginObject();
		JsonReaders.skipToken(jsonReader);
		Assert.assertThat(jsonReader.peek(), CoreMatchers.is(JsonToken.END_DOCUMENT));
	}

	@Test
	public void testSkipTokenForName()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("{\"foo\":1}"));
		jsonReader.beginObject();
		JsonReaders.skipToken(jsonReader);
		Assert.assertThat(jsonReader.nextInt(), CoreMatchers.is(1));
		jsonReader.endObject();
	}

	@Test
	public void testSkipTokenForString()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[\"foo\",\"bar\",\"baz\"]"));
		jsonReader.beginArray();
		Assert.assertThat(jsonReader.nextString(), CoreMatchers.is("foo"));
		JsonReaders.skipToken(jsonReader);
		Assert.assertThat(jsonReader.nextString(), CoreMatchers.is("baz"));
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForNumber()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[1,2,3]"));
		jsonReader.beginArray();
		Assert.assertThat(jsonReader.nextInt(), CoreMatchers.is(1));
		JsonReaders.skipToken(jsonReader);
		Assert.assertThat(jsonReader.nextInt(), CoreMatchers.is(3));
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForBoolean()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[false,true,true]"));
		jsonReader.beginArray();
		Assert.assertThat(jsonReader.nextBoolean(), CoreMatchers.is(false));
		JsonReaders.skipToken(jsonReader);
		Assert.assertThat(jsonReader.nextBoolean(), CoreMatchers.is(true));
		jsonReader.endArray();
	}

	@Test
	public void testSkipTokenForNull()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader("[null,true]"));
		jsonReader.beginArray();
		JsonReaders.skipToken(jsonReader);
		Assert.assertThat(jsonReader.nextBoolean(), CoreMatchers.is(true));
		jsonReader.endArray();
	}

	@Test(expected = EOFException.class)
	public void testSkipTokenForEndOfDocument()
			throws IOException {
		final JsonReader jsonReader = new JsonReader(new StringReader(""));
		JsonReaders.skipToken(jsonReader);
	}

}
