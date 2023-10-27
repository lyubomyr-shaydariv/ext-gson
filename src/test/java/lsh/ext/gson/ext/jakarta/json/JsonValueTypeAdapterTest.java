package lsh.ext.gson.ext.jakarta.json;

import java.util.List;
import javax.annotation.Nullable;

import jakarta.json.Json;
import jakarta.json.JsonValue;
import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonValueTypeAdapterTest
		extends AbstractTypeAdapterTest<JsonValue, JsonValue> {

	@Override
	protected JsonValue normalize(@Nullable final JsonValue value) {
		return value != null ? value : JsonValue.NULL;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						JsonValueTypeAdapter.getInstance(),
						"{\"boolean\":true,\"integer\":3,\"string\":\"foo\",\"null\":null,\"array\":[false,2,\"bar\",null]}",
						Json.createObjectBuilder()
								.add("boolean", true)
								.add("integer", 3)
								.add("string", "foo")
								.addNull("null")
								.add("array", Json.createArrayBuilder()
										.add(false)
										.add(2)
										.add("bar")
										.addNull()
										.build()
								)
								.build()
				)
		);
	}

}
