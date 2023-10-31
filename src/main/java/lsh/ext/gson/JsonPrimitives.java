package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonPrimitives {

	public static JsonPrimitive create(final Boolean b) {
		return new JsonPrimitive(b);
	}

	public static JsonPrimitive create(final Number n) {
		return new JsonPrimitive(n);
	}

	public static JsonPrimitive create(final String s) {
		return new JsonPrimitive(s);
	}

	public static JsonPrimitive create(final Character c) {
		return new JsonPrimitive(c);
	}

	public static JsonElement createOrNull(@Nullable final Boolean b) {
		return b != null ? new JsonPrimitive(b) : JsonNull.INSTANCE;
	}

	public static JsonElement createOrNull(@Nullable final Number n) {
		return n != null ? new JsonPrimitive(n) : JsonNull.INSTANCE;
	}

	public static JsonElement createOrNull(@Nullable final String s) {
		return s != null ? new JsonPrimitive(s) : JsonNull.INSTANCE;
	}

	public static JsonElement createOrNull(@Nullable final Character c) {
		return c != null ? new JsonPrimitive(c) : JsonNull.INSTANCE;
	}

}
