package lsh.ext.gson.adapters.jsonapi;

import java.io.IOException;
import javax.json.Json;
import javax.json.JsonValue;

import com.google.gson.TypeAdapter;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class JsonValueTypeAdapterTest {

	private static final JsonValue jsonValue = Json.createObjectBuilder()
			.add("boolean", true)
			.add("integer", 3)
			.add("string", "foo")
			.addNull("null")
			.add("array", Json.createArrayBuilder()
					.add(false)
					.add(2)
					.add("bar")
					.addNull()
					.build())
			.build();

	private static final String JSON_VALUE_JSON = "{\"boolean\":true,\"integer\":3,\"string\":\"foo\",\"null\":null,\"array\":[false,2,\"bar\",null]}";

	@Test
	public void testWrite() {
		final TypeAdapter<JsonValue> unit = JsonValueTypeAdapter.get();
		MatcherAssert.assertThat(unit.toJson(jsonValue), CoreMatchers.is(JSON_VALUE_JSON));
	}

	@Test
	public void testRead()
			throws IOException {
		final TypeAdapter<JsonValue> unit = JsonValueTypeAdapter.get();
		MatcherAssert.assertThat(unit.fromJson(JSON_VALUE_JSON), CoreMatchers.is(jsonValue));
	}

}
