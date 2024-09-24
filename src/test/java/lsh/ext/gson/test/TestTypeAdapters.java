package lsh.ext.gson.test;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.Gsons;

@UtilityClass
public final class TestTypeAdapters {

	private static final Gson source = Gsons.getNormalized();

	public static final TypeAdapter<Integer> primitiveIntTypeAdapter = source.getAdapter(int.class);

	public static final TypeAdapter<Boolean> booleanTypeAdapter = source.getAdapter(Boolean.class);
	public static final TypeAdapter<Integer> integerTypeAdapter = source.getAdapter(Integer.class);
	public static final TypeAdapter<String> stringTypeAdapter = source.getAdapter(String.class);

	@SuppressWarnings("unchecked")
	public static final TypeAdapter<List<String>> stringListTypeAdapter = (TypeAdapter<List<String>>) source.getAdapter(TypeToken.getParameterized(List.class, String.class));

	public static final TypeAdapter<Map<String, Integer>> stringToIntegerMapTypeAdapter = source.getAdapter(Types.stringToIntegerMapTypeToken);

}
