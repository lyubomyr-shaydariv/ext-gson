package lsh.ext.gson;

import java.util.List;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class ParameterizedTypesTest {

	@SuppressWarnings("unchecked")
	private static final TypeToken<List<String>> stringListTypeToken = (TypeToken<List<String>>) TypeToken.getParameterized(List.class, String.class);

	@Test
	public void testGetTypeArgumentForParameterizedType() {
		Assertions.assertEquals(String.class, ParameterizedTypes.getTypeArgument(stringListTypeToken.getType(), 0));
	}

	@Test
	public void testGetTypeArgumentForParameterizedTypeGoesBeyond() {
		Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> ParameterizedTypes.getTypeArgument(stringListTypeToken.getType(), 1));
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
