package lsh.ext.gson;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class ParameterizedTypesTest {

	private static final Type[] emptyTypeArray = {};

	@Test
	public void testResolveTypeArgumentsForRawTypes() {
		Assertions.assertArrayEquals(new Type[][] { new Type[] { Object.class } }, ParameterizedTypes.getTypeArguments(List.class));
	}

	@Test
	public void testResolveTypeArgumentsForParameterizedTypes() {
		Assertions.assertArrayEquals(new Type[][] { new Type[] { String.class } }, ParameterizedTypes.getTypeArguments(TypeToken.getParameterized(List.class, String.class).getType()));
		Assertions.assertArrayEquals(new Type[][] { new Type[] { Integer.class }, new Type[] { Float.class } }, ParameterizedTypes.getTypeArguments(TypeToken.getParameterized(Map.class, Integer.class, Float.class).getType()));
	}

	@Test
	public void testResolveTypeArgumentsForParameterizedTypesWithBounds() {
		Assertions.assertArrayEquals(new Type[][] { new Type[] { Number.class }, new Type[] { Serializable.class, new TypeToken<List<?>>() {}.getType() } }, ParameterizedTypes.getTypeArguments(GenericClassWithSomeBounds.class));
	}

	@Test
	public void testResolveTypeArgumentsForNonGenericType() {
		Assertions.assertArrayEquals(emptyTypeArray, ParameterizedTypes.getTypeArguments(String.class));
	}

	@SuppressWarnings("unused")
	private static final class GenericClassWithSomeBounds<N extends Number, T extends Serializable & List<?>> {
	}

}
