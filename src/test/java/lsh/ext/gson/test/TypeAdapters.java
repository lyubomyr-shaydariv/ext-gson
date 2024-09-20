package lsh.ext.gson.test;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.Gsons;

@UtilityClass
public final class TypeAdapters {

	private static final Gson typeAdapterSource = Gsons.getNormalized();

	public static final TypeAdapter<Integer> primitiveIntTypeAdapter = typeAdapterSource.getAdapter(int.class);

	public static final TypeAdapter<Boolean> booleanTypeAdapter = typeAdapterSource.getAdapter(Boolean.class);
	public static final TypeAdapter<Integer> integerTypeAdapter = typeAdapterSource.getAdapter(Integer.class);
	public static final TypeAdapter<String> stringTypeAdapter = typeAdapterSource.getAdapter(String.class);

	public static final TypeAdapter<Map<String, Integer>> stringToIntegerMapTypeAdapter = typeAdapterSource.getAdapter(Types.stringToIntegerMapTypeToken);

	public static final TypeAdapter<JsonElement> jsonElementTypeAdapter = typeAdapterSource.getAdapter(JsonElement.class);

}
