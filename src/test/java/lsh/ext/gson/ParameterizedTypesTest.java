package lsh.ext.gson;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class ParameterizedTypesTest {

	private static final Type listOfStringsType = TypeToken.getParameterized(List.class, String.class)
			.getType();

	@Test
	public void testGetTypeArgumentForParameterizedType() {
		Assertions.assertEquals(String.class, ParameterizedTypes.getTypeArgument(listOfStringsType, 0));
	}

	@Test
	public void testGetTypeArgumentForParameterizedTypeGoesBeyond() {
		Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> ParameterizedTypes.getTypeArgument(listOfStringsType, 1));
	}

	@Test
	public void testResolveTypeArgumentsForNonGenericType() {
		Assertions.assertNull(ParameterizedTypes.getTypeArgument(String.class, 0));
	}

	@Test
	public void testResolveTypeArgumentsForGenericType() {
		Assertions.assertNull(ParameterizedTypes.getTypeArgument(Object.class, 0));
	}

}
