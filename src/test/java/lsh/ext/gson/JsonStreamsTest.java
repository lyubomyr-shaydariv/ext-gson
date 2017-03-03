package lsh.ext.gson;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import org.junit.Test;

import static lsh.ext.gson.JsonStreams.copyTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
		copyTo(jsonReader, jsonWriter);
		assertThat(writer.toString(), is(NORMALIZED_JSON));
	}

	@Test
	public void testCopyToForMalformedJsonWithDefault()
			throws IOException {
		final Writer writer = new StringWriter();
		final JsonReader jsonReader = newLenientJsonReader(UNNORMALIZED_JSON_WITH_TRAILING_BRACE);
		final JsonWriter jsonWriter = new JsonWriter(writer);
		copyTo(jsonReader, jsonWriter);
		assertThat(writer.toString(), is(NORMALIZED_JSON));
	}

	@Test
	public void testCopyTo()
			throws IOException {
		final Writer writer = new StringWriter();
		final JsonReader jsonReader = newLenientJsonReader(UNNORMALIZED_JSON);
		final JsonWriter jsonWriter = new JsonWriter(writer);
		copyTo(jsonReader, jsonWriter, true);
		assertThat(writer.toString(), is(NORMALIZED_JSON));
	}

	@Test
	public void testCopyToForMalformedJson()
			throws IOException {
		final Writer writer = new StringWriter();
		final JsonReader jsonReader = newLenientJsonReader(UNNORMALIZED_JSON_WITH_TRAILING_BRACE);
		final JsonWriter jsonWriter = new JsonWriter(writer);
		copyTo(jsonReader, jsonWriter, true);
		assertThat(writer.toString(), is(NORMALIZED_JSON));
	}

	@Test(expected = MalformedJsonException.class)
	public void testCopyToForMalformedJsonDisallowingLenient()
			throws IOException {
		final Writer writer = new StringWriter();
		final JsonReader jsonReader = newLenientJsonReader(UNNORMALIZED_JSON_WITH_TRAILING_BRACE);
		final JsonWriter jsonWriter = new JsonWriter(writer);
		copyTo(jsonReader, jsonWriter, false);
	}

	private static JsonReader newLenientJsonReader(final String json) {
		final JsonReader reader = new JsonReader(new StringReader(json));
		reader.setLenient(true);
		return reader;
	}

}
