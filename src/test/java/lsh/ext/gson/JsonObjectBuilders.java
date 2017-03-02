package lsh.ext.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

final class JsonObjectBuilders {

	private JsonObjectBuilders() {
	}

	static JsonObject jsonObject(
			final String key1, final JsonElement value1,
			final String key2, final JsonElement value2,
			final String key3, final JsonElement value3
	) {
		final JsonObject jsonObject = new JsonObject();
		jsonObject.add(key1, value1);
		jsonObject.add(key2, value2);
		jsonObject.add(key3, value3);
		return jsonObject;
	}

}
