package lsh.ext.gson;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class JsonArrays {

	public static JsonArray of() {
		return new JsonArray(0);
	}

	public static JsonArray of(
			@Nullable final JsonElement e1
	) {
		final JsonArray jsonArray = new JsonArray(1);
		jsonArray.add(e1);
		return jsonArray;
	}

	public static JsonArray of(
			@Nullable final JsonElement e1,
			@Nullable final JsonElement e2
	) {
		final JsonArray jsonArray = new JsonArray(2);
		jsonArray.add(e1);
		jsonArray.add(e2);
		return jsonArray;
	}

	public static JsonArray of(
			@Nullable final JsonElement e1,
			@Nullable final JsonElement e2,
			@Nullable final JsonElement e3
	) {
		final JsonArray jsonArray = new JsonArray(3);
		jsonArray.add(e1);
		jsonArray.add(e2);
		jsonArray.add(e3);
		return jsonArray;
	}

	public static JsonArray of(
			@Nullable final JsonElement e1,
			@Nullable final JsonElement e2,
			@Nullable final JsonElement e3,
			@Nullable final JsonElement e4
	) {
		final JsonArray jsonArray = new JsonArray(4);
		jsonArray.add(e1);
		jsonArray.add(e2);
		jsonArray.add(e3);
		jsonArray.add(e4);
		return jsonArray;
	}

	public static JsonArray of(final JsonElement... jsonElements) {
		final JsonArray jsonArray = new JsonArray(jsonElements.length);
		for ( final JsonElement e : jsonElements ) {
			jsonArray.add(e);
		}
		return jsonArray;
	}

	public static JsonArray from(final Iterable<? extends JsonElement> jsonElements) {
		final JsonArray jsonArray;
		if ( jsonElements instanceof final Collection<?> collection ) {
			jsonArray = new JsonArray(collection.size());
		} else {
			jsonArray = new JsonArray();
		}
		for ( final JsonElement jsonElement : jsonElements ) {
			jsonArray.add(jsonElement);
		}
		return jsonArray;
	}

	public static List<JsonElement> asImmutableList(final JsonArray jsonArray) {
		return Collections.unmodifiableList(jsonArray.asList());
	}

	public static List<JsonElement> asMutableList(final JsonArray jsonArray) {
		return jsonArray.asList();
	}

}
