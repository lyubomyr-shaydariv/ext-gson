package lsh.ext.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class ParameterizedTypesTest {

	private static final Type[] emptyTypeArray = new Type[0];

	@Test
	public void testResolveTypeArgumentsForRawTypes() {
		Assertions.assertArrayEquals(new Type[][]{ new Type[]{ Object.class } }, ParameterizedTypes.getTypeArguments(List.class));
	}

	@Test
	public void testResolveTypeArgumentsForParameterizedTypes() {
		Assertions.assertArrayEquals(new Type[][]{ new Type[]{ String.class } }, ParameterizedTypes.getTypeArguments(ParameterizedTypes.listOf(String.class)));
		Assertions.assertArrayEquals(new Type[][]{ new Type[]{ Integer.class }, new Type[]{ Float.class } }, ParameterizedTypes.getTypeArguments(ParameterizedTypes.mapOf(Integer.class, Float.class)));
	}

	@Test
	public void testResolveTypeArgumentsForParameterizedTypesWithBounds() {
		Assertions.assertArrayEquals(new Type[][]{ new Type[]{ Number.class }, new Type[]{ CharSequence.class, new TypeToken<List<?>>() {}.getType() } }, ParameterizedTypes.getTypeArguments(GenericClassWithSomeBounds.class));
	}

	@Test
	public void testResolveTypeArgumentsForNonGenericType() {
		Assertions.assertArrayEquals(emptyTypeArray, ParameterizedTypes.getTypeArguments(String.class));
	}

	@Test
	public void testListType() {
		assertParameterized(ParameterizedTypes.listOf(String.class), List.class, String.class);
		assertParameterized(ParameterizedTypes.listOf(Void.class), List.class, Void.class);
		assertParameterized(ParameterizedTypes.listOf(Object.class), List.class, Object.class);
	}

	@Test
	public void testSetType() {
		assertParameterized(ParameterizedTypes.setOf(String.class), Set.class, String.class);
		assertParameterized(ParameterizedTypes.setOf(Void.class), Set.class, Void.class);
		assertParameterized(ParameterizedTypes.setOf(Object.class), Set.class, Object.class);
	}

	@Test
	public void testMapType() {
		assertParameterized(ParameterizedTypes.mapOf(String.class, Void.class), Map.class, String.class, Void.class);
		assertParameterized(ParameterizedTypes.mapOf(Void.class, Object.class), Map.class, Void.class, Object.class);
		assertParameterized(ParameterizedTypes.mapOf(Object.class, String.class), Map.class, Object.class, String.class);
	}

	@SuppressWarnings("unused")
	private static final class GenericClassWithSomeBounds<N extends Number, CS extends CharSequence & List<?>> {
	}

	private static void assertParameterized(final ParameterizedType actualParameterizedType, final Class<?> expectedRawType,
			final Type... expectedTypeArguments) {
		Assertions.assertEquals(expectedRawType, actualParameterizedType.getRawType());
		Assertions.assertArrayEquals(expectedTypeArguments, actualParameterizedType.getActualTypeArguments());
	}

}
