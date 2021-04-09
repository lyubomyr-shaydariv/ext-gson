package lsh.ext.gson.ext.json;

import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.json.Json;
import javax.json.JsonValue;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonValueTypeAdapterTest
		extends AbstractTypeAdapterTest<JsonValue, JsonValue> {

	@Nullable
	@Override
	protected JsonValue finalize(@Nullable final JsonValue value) {
		return value != null ? value : JsonValue.NULL;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						JsonValueTypeAdapter.get(),
						"{\"boolean\":true,\"integer\":3,\"string\":\"foo\",\"null\":null,\"array\":[false,2,\"bar\",null]}",
						() -> Json.createObjectBuilder()
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
								).build()
				)
		);
	}

}
