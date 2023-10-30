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

	public static Collector<Map.Entry<String, ? extends JsonElement>, ?, JsonObject> toJsonObject() {
		return toJsonObject(JsonObject::new);
	}

	public static Collector<Map.Entry<String, ? extends JsonElement>, ?, JsonObject> toJsonObject(final Supplier<JsonObject> jsonObjectSupplier) {
		return Collector.of(
				jsonObjectSupplier,
				(o, e) -> o.add(e.getKey(), e.getValue()),
				(o1, o2) -> {
					for ( final Map.Entry<String, JsonElement> e : o2.entrySet() ) {
						o1.add(e.getKey(), e.getValue());
					}
					return o1;
				}
		);
	}

	public static Collector<JsonElement, ?, JsonArray> toJsonArray() {
		return toJsonArray(JsonArray::new);
	}

	public static Collector<JsonElement, ?, JsonArray> toJsonArray(final Supplier<JsonArray> jsonArraySupplier) {
		return Collector.of(
				jsonArraySupplier,
				JsonArray::add,
				(o1, o2) -> {
					o1.addAll(o2);
					return o1;
				}
		);
	}

}
