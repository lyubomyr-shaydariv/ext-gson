package lsh.ext.gson;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.stream.Stream;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public final class JsonStreamsTest {

	private static final String NORMALIZED_JSON = "{\"foo\":\"foo\",\"bar\":\"bar\",\"baz\":3.141592653589793238462643383279}";
	private static final String UNNORMALIZED_JSON = "{\n\t\"foo\": \"foo\",\n\tbar: \"bar\"\n,\t\"baz\": 3.141592653589793238462643383279\n}";
	private static final String UNNORMALIZED_JSON_WITH_TRAILING_BRACE = UNNORMALIZED_JSON + '}';

	@Test
	public void testCopyToWithDefault()
			throws IOException {
		final Writer writer = new StringWriter();
		final JsonReader jsonReader = newLenientJsonReader(UNNORMALIZED_JSON);
		final JsonWriter jsonWriter = new JsonWriter(writer);
		JsonStreams.copyTo(jsonReader, jsonWriter);
		Assertions.assertEquals(NORMALIZED_JSON, writer.toString());
	}

	@Test
	public void testCopyToForMalformedJsonWithDefault()
			throws IOException {
		final Writer writer = new StringWriter();
		final JsonReader jsonReader = newLenientJsonReader(UNNORMALIZED_JSON_WITH_TRAILING_BRACE);
		final JsonWriter jsonWriter = new JsonWriter(writer);
		JsonStreams.copyTo(jsonReader, jsonWriter);
		Assertions.assertEquals(NORMALIZED_JSON, writer.toString());
	}

	@Test
	public void testCopyTo()
			throws IOException {
		final Writer writer = new StringWriter();
		final JsonReader jsonReader = newLenientJsonReader(UNNORMALIZED_JSON);
		final JsonWriter jsonWriter = new JsonWriter(writer);
		JsonStreams.copyTo(jsonReader, jsonWriter, true);
		Assertions.assertEquals(NORMALIZED_JSON, writer.toString());
	}

	@Test
	public void testCopyToForMalformedJson()
			throws IOException {
		final Writer writer = new StringWriter();
		final JsonReader jsonReader = newLenientJsonReader(UNNORMALIZED_JSON_WITH_TRAILING_BRACE);
		final JsonWriter jsonWriter = new JsonWriter(writer);
		JsonStreams.copyTo(jsonReader, jsonWriter, true);
		Assertions.assertEquals(NORMALIZED_JSON, writer.toString());
	}

	@Test
	public void testCopyToForMalformedJsonDisallowingLenient()
			throws IOException {
		try ( final JsonReader jsonReader = newLenientJsonReader(UNNORMALIZED_JSON_WITH_TRAILING_BRACE);
			  JsonWriter jsonWriter = new JsonWriter(new StringWriter()) ) {
			Assertions.assertThrows(MalformedJsonException.class, () -> JsonStreams.copyTo(jsonReader, jsonWriter, false));
		}
	}

	private static Stream<Arguments> testCopyToForMultipleTopLevelValues() {
		return Stream.of(
				Arguments.of("false", "1000", "true"),
				Arguments.of("1", "\"foo\"", "{}", "[1,2,3]"),
				Arguments.of("\"only\"")
		);
	}

	@MethodSource
	@ParameterizedTest
	public void testCopyToForMultipleTopLevelValues(
			@AggregateWith(VariadicArgumentsAggregator.class) final String... jsonDocuments
	)
			throws IOException {
		assert jsonDocuments.length > 0;
		final JsonReader jsonReader = newLenientJsonReader(String.join(" ", jsonDocuments));
		for ( final String jsonDocument : jsonDocuments ) {
			final Writer writer = new StringWriter();
			JsonStreams.copyTo(jsonReader, new JsonWriter(writer), true);
			Assertions.assertEquals(jsonDocument, writer.toString());
		}
	}

	@SuppressWarnings("deprecation")
	private static JsonReader newLenientJsonReader(final String json) {
		final JsonReader reader = new JsonReader(new StringReader(json));
		reader.setLenient(true);
		return reader;
	}

}
