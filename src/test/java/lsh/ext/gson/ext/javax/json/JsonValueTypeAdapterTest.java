package lsh.ext.gson.ext.javax.json;

import java.util.List;
import javax.annotation.Nullable;
import javax.json.JsonValue;
import javax.json.spi.JsonProvider;

import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonValueTypeAdapterTest
		extends AbstractTypeAdapterTest<JsonValue, JsonValue> {

	private static final JsonProvider jsonProvider = JsonProvider.provider();

	@Override
	protected JsonValue normalize(@Nullable final JsonValue value) {
		return value != null ? value : JsonValue.NULL;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						JavaJsonValueTypeAdapter.getInstance(),
						"{\"boolean\":true,\"integer\":3,\"string\":\"foo\",\"null\":null,\"array\":[false,2,\"bar\",null]}",
						jsonProvider.createObjectBuilder()
								.add("boolean", true)
								.add("integer", 3)
								.add("string", "foo")
								.addNull("null")
								.add("array", jsonProvider.createArrayBuilder()
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
