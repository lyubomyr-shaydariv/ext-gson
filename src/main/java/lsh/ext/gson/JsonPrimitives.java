package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonPrimitives {

	public static final JsonPrimitive falseJsonPrimitive = new JsonPrimitive(false);
	public static final JsonPrimitive trueJsonPrimitive = new JsonPrimitive(true);

	public static JsonPrimitive of(final boolean b) {
		return b ? trueJsonPrimitive : falseJsonPrimitive;
	}

	public static JsonElement orNull(@Nullable final Boolean b) {
		return b != null ? of(b) : JsonNull.INSTANCE;
	}

	public static JsonElement orNull(@Nullable final Number n) {
		return n != null ? new JsonPrimitive(n) : JsonNull.INSTANCE;
	}

	public static JsonElement orNull(@Nullable final String s) {
		return s != null ? new JsonPrimitive(s) : JsonNull.INSTANCE;
	}

	public static JsonElement orNull(@Nullable final Character c) {
		return c != null ? new JsonPrimitive(c) : JsonNull.INSTANCE;
	}

	public static JsonElement orNull(@Nullable final JsonElement jsonElement) {
		return jsonElement != null ? jsonElement : JsonNull.INSTANCE;
	}

}
