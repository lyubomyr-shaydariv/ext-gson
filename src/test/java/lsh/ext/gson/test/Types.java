package lsh.ext.gson.test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class Types {

	@SuppressWarnings("RedundantUnmodifiable")
	public static final Class<?> jdkUnmodifiableListClass = Collections.unmodifiableList(Collections.emptyList())
			.getClass();

	@SuppressWarnings("RedundantUnmodifiable")
	public static final Class<?> jdkUnmodifiableMapClass = Collections.unmodifiableMap(Collections.emptyMap())
			.getClass();

	public static final TypeToken<Void> primitiveVoidTypeToken = TypeToken.get(void.class);

	public static final TypeToken<Integer> primitiveIntTypeToken = TypeToken.get(int.class);

	public static final TypeToken<Boolean> booleanTypeToken = TypeToken.get(Boolean.class);
	public static final TypeToken<Integer> integerTypeToken = TypeToken.get(Integer.class);
	public static final TypeToken<Object> objectTypeToken = TypeToken.get(Object.class);
	public static final TypeToken<String> stringTypeToken = TypeToken.get(String.class);

	@SuppressWarnings("rawtypes")
	public static TypeToken<Set> rawSetTypeToken = TypeToken.get(Set.class);
	@SuppressWarnings("rawtypes")
	public static TypeToken<Map> rawMapTypeToken = TypeToken.get(Map.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final TypeToken<List<Integer>> integerListTypeToken = (TypeToken) TypeToken.getParameterized(List.class, Integer.class);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final TypeToken<List<String>> stringListTypeToken = (TypeToken) TypeToken.getParameterized(List.class, String.class);

}
