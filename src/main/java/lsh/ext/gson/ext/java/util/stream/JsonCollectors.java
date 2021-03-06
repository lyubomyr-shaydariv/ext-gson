package lsh.ext.gson.ext.java.util.stream;

import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * <p>Contains various implementations of {@link Collector} for {@link JsonElement}.</p>
 *
 * @author Lyubomyr Shaydariv
 * @since 0-SNAPSHOT
 */
public final class JsonCollectors {

	private static final Collector<Map.Entry<String, ? extends JsonElement>, JsonObject, JsonObject> toNewJsonObject = createToJsonObject(JsonObject::new);
	private static final Collector<JsonElement, JsonArray, JsonArray> toNewJsonArray = createToJsonArray(JsonArray::new);

	private JsonCollectors() {
	}

	/**
	 * @return A collector to collect entries to a new {@link JsonObject}.
	 */
	public static Collector<Map.Entry<String, ? extends JsonElement>, JsonObject, JsonObject> toJsonObject() {
		return toNewJsonObject;
	}

	/**
	 * @param jsonObjectSupplier Supplier to provide a {@link JsonObject} to collect to.
	 *
	 * @return A collector to collect entries to an existing {@link JsonObject}.
	 */
	public static Collector<Map.Entry<String, ? extends JsonElement>, JsonObject, JsonObject> toJsonObject(final Supplier<JsonObject> jsonObjectSupplier) {
		return createToJsonObject(jsonObjectSupplier);
	}

	/**
	 * @return A collector to collect entries to a new {@link JsonArray}.
	 */
	public static Collector<JsonElement, JsonArray, JsonArray> toJsonArray() {
		return toNewJsonArray;
	}

	/**
	 * @param jsonArraySupplier Supplier to provide a {@link JsonArray} to collect to.
	 *
	 * @return A collector to collect entries to an existing {@link JsonArray}.
	 */
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
