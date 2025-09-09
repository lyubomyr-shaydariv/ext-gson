package lsh.ext.gson;

import java.util.Map;
import javax.annotation.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonObjects {

	public static JsonObject of() {
		return new JsonObject();
	}

	public static JsonObject of(
			final String k1, @Nullable final JsonElement v1
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		return jsonObject;
	}

	public static JsonObject of(
			final String k1, @Nullable final JsonElement v1,
			final String k2, @Nullable final JsonElement v2
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		jsonObject.add(k2, v2);
		return jsonObject;
	}

	public static JsonObject of(
			final String k1, @Nullable final JsonElement v1,
			final String k2, @Nullable final JsonElement v2,
			final String k3, @Nullable final JsonElement v3
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		jsonObject.add(k2, v2);
		jsonObject.add(k3, v3);
		return jsonObject;
	}

	public static JsonObject of(
			final String k1, @Nullable final JsonElement v1,
			final String k2, @Nullable final JsonElement v2,
			final String k3, @Nullable final JsonElement v3,
			final String k4, @Nullable final JsonElement v4
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(k1, v1);
		jsonObject.add(k2, v2);
		jsonObject.add(k3, v3);
		jsonObject.add(k4, v4);
		return jsonObject;
	}

	public static JsonObject from(final Map<String, ? extends JsonElement> map) {
		final JsonObject jsonObject = new JsonObject();
		for ( final Map.Entry<String, ? extends JsonElement> e : map.entrySet() ) {
			jsonObject.add(e.getKey(), e.getValue());
		}
		return jsonObject;
	}

	@SafeVarargs
	public static JsonObject from(final Map.Entry<String, ? extends JsonElement>... entries) {
		final JsonObject jsonObject = new JsonObject();
		for ( final Map.Entry<String, ? extends JsonElement> e : entries ) {
			jsonObject.add(e.getKey(), e.getValue());
		}
		return jsonObject;
	}

	public static JsonObject from(final Iterable<? extends Map.Entry<String, ? extends JsonElement>> entries) {
		final JsonObject jsonObject = new JsonObject();
		for ( final Map.Entry<String, ? extends JsonElement> e : entries ) {
			jsonObject.add(e.getKey(), e.getValue());
		}
		return jsonObject;
	}

}
