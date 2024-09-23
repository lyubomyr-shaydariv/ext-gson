package lsh.ext.gson.test;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import lombok.experimental.UtilityClass;
import lsh.ext.gson.Gsons;

@UtilityClass
public final class TestTypeAdapters {

	private static final Gson source = Gsons.getNormalized();

	public static final TypeAdapter<Integer> primitiveIntTypeAdapter = source.getAdapter(int.class);

	public static final TypeAdapter<Boolean> booleanTypeAdapter = source.getAdapter(Boolean.class);
	public static final TypeAdapter<Integer> integerTypeAdapter = source.getAdapter(Integer.class);
	public static final TypeAdapter<String> stringTypeAdapter = source.getAdapter(String.class);

	public static final TypeAdapter<Map<String, Integer>> stringToIntegerMapTypeAdapter = source.getAdapter(Types.stringToIntegerMapTypeToken);

}
