package lsh.ext.gson.adapters.jsonapi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.json.Json;
import javax.json.JsonValue;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;

public final class JsonValueTypeAdapterTest
		extends AbstractTypeAdapterTest<JsonValue> {

	@Nonnull
	@Override
	protected TypeAdapter<JsonValue> createUnit(@Nonnull final Gson gson) {
		return JsonValueTypeAdapter.get();
	}

	@Nullable
	@Override
	protected JsonValue nullValue() {
		return JsonValue.NULL;
	}

	@Nonnull
	@Override
	protected JsonValue getValue() {
		return Json.createObjectBuilder()
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
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final JsonValue value) {
		return value;
	}

	@Nonnull
	@Override
	protected String getValueJson() {
		return "{\"boolean\":true,\"integer\":3,\"string\":\"foo\",\"null\":null,\"array\":[false,2,\"bar\",null]}";
	}

}
