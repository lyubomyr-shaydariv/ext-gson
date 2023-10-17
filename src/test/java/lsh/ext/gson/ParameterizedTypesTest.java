package lsh.ext.gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class ParameterizedTypesTest {

	private static final Type[] emptyTypeArray = {};

	@Test
	public void testResolveTypeArgumentsForParameterizedTypes() {
		Assertions.assertArrayEquals(new Type[] { String.class }, ParameterizedTypes.getTypeArguments(TypeToken.getParameterized(List.class, String.class).getType()));
		Assertions.assertArrayEquals(new Type[] { Integer.class, Float.class }, ParameterizedTypes.getTypeArguments(TypeToken.getParameterized(Map.class, Integer.class, Float.class).getType()));
	}

	@Test
	public void testResolveTypeArgumentsForNonGenericType() {
		Assertions.assertArrayEquals(emptyTypeArray, ParameterizedTypes.getTypeArguments(String.class));
	}

	@Test
	public void testResolveTypeArgumentsForGenericType() {
		Assertions.assertArrayEquals(emptyTypeArray, ParameterizedTypes.getTypeArguments(Object.class));
	}

}
