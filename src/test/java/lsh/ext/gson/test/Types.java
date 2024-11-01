package lsh.ext.gson.test;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import java.util.stream.Stream;

import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class Types {

	public static <T> TypeToken<T> typeTokenOf(final Type rawType, final Type... typeArguments) {
		final TypeToken<?> typeToken;
		if ( typeArguments.length == 0 ) {
			typeToken = TypeToken.get(rawType);
		} else {
			typeToken = TypeToken.getParameterized(rawType, typeArguments);
		}
		@SuppressWarnings("unchecked")
		final TypeToken<T> castTypeToken = (TypeToken<T>) typeToken;
		return castTypeToken;
	}

	@SuppressWarnings("RedundantUnmodifiable")
	public static final Class<?> jdkUnmodifiableListClass = Collections.unmodifiableList(Collections.emptyList())
			.getClass();

	@SuppressWarnings("RedundantUnmodifiable")
	public static final Class<?> jdkUnmodifiableMapClass = Collections.unmodifiableMap(Collections.emptyMap())
			.getClass();

	public static final TypeToken<Void> primitiveVoidTypeToken = TypeToken.get(void.class);
	public static final TypeToken<Integer> primitiveIntTypeToken = TypeToken.get(int.class);

	public static final TypeToken<Integer> integerTypeToken = TypeToken.get(Integer.class);
	public static final TypeToken<Object> objectTypeToken = TypeToken.get(Object.class);
	public static final TypeToken<String> stringTypeToken = TypeToken.get(String.class);

	public static final TypeToken<Collection<?>> collectionTypeToken = typeTokenOf(Collection.class);
	public static final TypeToken<List<?>> rawListTypeToken = typeTokenOf(List.class);
	public static final TypeToken<Set<?>> rawSetTypeToken = typeTokenOf(Set.class);
	public static final TypeToken<Map<?, ?>> rawMapTypeToken = typeTokenOf(Map.class);

	public static final TypeToken<List<Integer>> integerListTypeToken = typeTokenOf(List.class, Integer.class);
	public static final TypeToken<List<String>> stringListTypeToken = typeTokenOf(List.class, String.class);

	public static final TypeToken<Map<String, Integer>> stringToIntegerMapTypeToken = typeTokenOf(Map.class, String.class, Integer.class);

	public static final TypeToken<Stream<Integer>> integerStreamTypeToken = typeTokenOf(Stream.class, Integer.class);

	public static final TypeToken<OptionalInt> optionalIntTypeToken = TypeToken.get(OptionalInt.class);
	public static final TypeToken<OptionalLong> optionalLongTypeToken = TypeToken.get(OptionalLong.class);
	public static final TypeToken<OptionalDouble> optionalDoubleTypeToken = TypeToken.get(OptionalDouble.class);

	public static final TypeToken<Optional<Integer>> optionalIntegerTypeToken = typeTokenOf(Optional.class, Integer.class);
	public static final TypeToken<Optional<Object>> optionalObjectTypeToken = typeTokenOf(Optional.class, Object.class);
	public static final TypeToken<Optional<String>> optionalStringTypeToken = typeTokenOf(Optional.class, String.class);

}
