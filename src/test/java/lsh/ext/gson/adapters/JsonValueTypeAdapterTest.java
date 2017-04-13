package lsh.ext.gson.adapters;

import java.io.IOException;
import javax.json.JsonValue;

import com.google.gson.TypeAdapter;
import org.junit.Test;

import static javax.json.Json.createArrayBuilder;
import static javax.json.Json.createObjectBuilder;

import static lsh.ext.gson.adapters.JsonValueTypeAdapter.getJsonValueTypeAdapter;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public final class JsonValueTypeAdapterTest {

	private static final JsonValue jsonValue = createObjectBuilder()
			.add("boolean", true)
			.add("integer", 3)
			.add("string", "foo")
			.addNull("null")
			.add("array", createArrayBuilder()
					.add(false)
					.add(2)
					.add("bar")
					.addNull()
					.build())
			.build();

	private static final String JSON_VALUE_JSON = "{\"boolean\":true,\"integer\":3,\"string\":\"foo\",\"null\":null,\"array\":[false,2,\"bar\",null]}";

	@Test
	public void testWrite() {
		final TypeAdapter<JsonValue> unit = getJsonValueTypeAdapter();
		assertThat(unit.toJson(jsonValue), is(JSON_VALUE_JSON));
	}

	@Test
	public void testRead()
			throws IOException {
		final TypeAdapter<JsonValue> unit = getJsonValueTypeAdapter();
		assertThat(unit.fromJson(JSON_VALUE_JSON), is(jsonValue));
	}

}
