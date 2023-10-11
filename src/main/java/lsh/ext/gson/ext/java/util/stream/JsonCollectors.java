package lsh.ext.gson.ext.java.util.stream;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonCollectors {

	private static final Collector<Map.Entry<String, ? extends JsonElement>, JsonObject, JsonObject> toNewJsonObject = createToJsonObject(JsonObject::new);
	private static final Collector<JsonElement, JsonArray, JsonArray> toNewJsonArray = createToJsonArray(JsonArray::new);

	public static Collector<Map.Entry<String, ? extends JsonElement>, JsonObject, JsonObject> toJsonObject() {
		return toNewJsonObject;
	}

	public static Collector<Map.Entry<String, ? extends JsonElement>, JsonObject, JsonObject> toJsonObject(final Supplier<JsonObject> jsonObjectSupplier) {
		return createToJsonObject(jsonObjectSupplier);
	}

	public static Collector<JsonElement, JsonArray, JsonArray> toJsonArray() {
		return toNewJsonArray;
	}

	public static Collector<JsonElement, JsonArray, JsonArray> toJsonArray(final Supplier<JsonArray> jsonArraySupplier) {
		return createToJsonArray(jsonArraySupplier);
	}

	private static Collector<Map.Entry<String, ? extends JsonElement>, JsonObject, JsonObject> createToJsonObject(
			final Supplier<JsonObject> jsonObjectSupplier) {
		return Collector.of(
				jsonObjectSupplier,
				(jsonObject1, e) -> jsonObject1.add(e.getKey(), e.getValue()),
				(jsonObject1, jsonObject2) -> {
					for ( final Map.Entry<String, JsonElement> e : jsonObject2.entrySet() ) {
						jsonObject1.add(e.getKey(), e.getValue());
					}
					return jsonObject1;
				}
		);
	}

	private static Collector<JsonElement, JsonArray, JsonArray> createToJsonArray(final Supplier<JsonArray> jsonArraySupplier) {
		return Collector.of(
				jsonArraySupplier,
				JsonArray::add,
				(jsonArray1, jsonArray2) -> {
					jsonArray1.addAll(jsonArray2);
					return jsonArray1;
				}
		);
	}

}
