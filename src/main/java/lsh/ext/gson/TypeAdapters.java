package lsh.ext.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class TypeAdapters {

	private static final Gson source = Gsons.getNormalized();

	@Getter
	private static final TypeAdapter<JsonElement> jsonElementTypeAdapter = source.getAdapter(JsonElement.class);

}
