package lsh.ext.gson.adapters.jsonapi;

import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.json.Json;
import javax.json.JsonValue;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class JsonValueTypeAdapterTest
		extends AbstractTypeAdapterTest<JsonValue> {

	public JsonValueTypeAdapterTest() {
		super(JsonValue.NULL);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final JsonValue value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				Arguments.of(JsonValueTypeAdapter.get(), "{\"boolean\":true,\"integer\":3,\"string\":\"foo\",\"null\":null,\"array\":[false,2,\"bar\",null]}", (Supplier<?>) () -> Json.createObjectBuilder().add("boolean", true).add("integer", 3).add("string", "foo").addNull("null").add("array", Json.createArrayBuilder().add(false).add(2).add("bar").addNull().build()).build())
		);
	}

}
