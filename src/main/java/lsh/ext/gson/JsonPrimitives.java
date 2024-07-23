package lsh.ext.gson;

import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonPrimitives {

	/**
	 * Represents the `false` constant JSON primitive instance.
	 */
	public static final JsonPrimitive falseJsonPrimitive = new JsonPrimitive(false);

	/**
	 * Represents the `false` constant JSON primitive instance.
	 */
	public static final JsonPrimitive trueJsonPrimitive = new JsonPrimitive(true);

	public static JsonPrimitive of(final boolean b) {
		if ( !b ) {
			return falseJsonPrimitive;
		}
		return trueJsonPrimitive;
	}

	public static JsonElement orNullable(@Nullable final Boolean b) {
		if ( b == null ) {
			return JsonNull.INSTANCE;
		}
		return of(b);
	}

	public static JsonElement orNullable(@Nullable final Number n) {
		if ( n == null ) {
			return JsonNull.INSTANCE;
		}
		return new JsonPrimitive(n);
	}

	public static JsonElement orNullable(@Nullable final String s) {
		if ( s == null ) {
			return JsonNull.INSTANCE;
		}
		return new JsonPrimitive(s);
	}

	public static JsonElement orNullable(@Nullable final Character c) {
		if ( c == null ) {
			return JsonNull.INSTANCE;
		}
		return new JsonPrimitive(c);
	}

	public static JsonElement orNullable(@Nullable final JsonElement jsonElement) {
		if ( jsonElement == null ) {
			return JsonNull.INSTANCE;
		}
		return jsonElement;
	}

}
