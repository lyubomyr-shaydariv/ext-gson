package lsh.ext.gson;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class JsonStreamsTest {

	private static final String NORMALIZED_JSON = "{\"foo\":\"foo\",\"bar\":\"bar\"}";
	private static final String UNNORMALIZED_JSON = "{\n\t\"foo\": \"foo\",\n\tbar: \"bar\"\n}";
	private static final String UNNORMALIZED_JSON_WITH_TRAILING_BRACE = UNNORMALIZED_JSON + "}";

	@Test
	public void testCopyToWithDefault()
			throws IOException {
		final Writer writer = new StringWriter();
		final JsonReader jsonReader = newLenientJsonReader(UNNORMALIZED_JSON);
		final JsonWriter jsonWriter = new JsonWriter(writer);
		JsonStreams.copyTo(jsonReader, jsonWriter);
		MatcherAssert.assertThat(writer.toString(), CoreMatchers.is(NORMALIZED_JSON));
	}

	@Test
	public void testCopyToForMalformedJsonWithDefault()
			throws IOException {
		final Writer writer = new StringWriter();
		final JsonReader jsonReader = newLenientJsonReader(UNNORMALIZED_JSON_WITH_TRAILING_BRACE);
		final JsonWriter jsonWriter = new JsonWriter(writer);
		JsonStreams.copyTo(jsonReader, jsonWriter);
		MatcherAssert.assertThat(writer.toString(), CoreMatchers.is(NORMALIZED_JSON));
	}

	@Test
	public void testCopyTo()
			throws IOException {
		final Writer writer = new StringWriter();
		final JsonReader jsonReader = newLenientJsonReader(UNNORMALIZED_JSON);
		final JsonWriter jsonWriter = new JsonWriter(writer);
		JsonStreams.copyTo(jsonReader, jsonWriter, true);
		MatcherAssert.assertThat(writer.toString(), CoreMatchers.is(NORMALIZED_JSON));
	}

	@Test
	public void testCopyToForMalformedJson()
			throws IOException {
		final Writer writer = new StringWriter();
		final JsonReader jsonReader = newLenientJsonReader(UNNORMALIZED_JSON_WITH_TRAILING_BRACE);
		final JsonWriter jsonWriter = new JsonWriter(writer);
		JsonStreams.copyTo(jsonReader, jsonWriter, true);
		MatcherAssert.assertThat(writer.toString(), CoreMatchers.is(NORMALIZED_JSON));
	}

	@Test(expected = MalformedJsonException.class)
	public void testCopyToForMalformedJsonDisallowingLenient()
			throws IOException {
		final Writer writer = new StringWriter();
		final JsonReader jsonReader = newLenientJsonReader(UNNORMALIZED_JSON_WITH_TRAILING_BRACE);
		final JsonWriter jsonWriter = new JsonWriter(writer);
		JsonStreams.copyTo(jsonReader, jsonWriter, false);
	}

	private static JsonReader newLenientJsonReader(final String json) {
		final JsonReader reader = new JsonReader(new StringReader(json));
		reader.setLenient(true);
		return reader;
	}

}
