package lsh.ext.gson.adapters.jsonapi;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.json.Json;
import javax.json.JsonValue;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class JsonValueTypeAdapterTest
		extends AbstractTypeAdapterTest<JsonValue> {

	@Parameterized.Parameters
	public static Iterable<TestWith<JsonValue>> parameters() {
		return ImmutableList.of(
				testWith(
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
										.build())
								.build(),
						"{\"boolean\":true,\"integer\":3,\"string\":\"foo\",\"null\":null,\"array\":[false,2,\"bar\",null]}"
				)
		);
	}

	public JsonValueTypeAdapterTest(final TestWith<? extends JsonValue> testWith) {
		super(JsonValue.NULL, testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends JsonValue> createUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return JsonValueTypeAdapter.get();
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final JsonValue value) {
		return value;
	}

}
