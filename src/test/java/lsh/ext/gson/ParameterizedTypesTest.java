package lsh.ext.gson;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

	@ValueSource(classes = {
			void.class,
			int.class,
			Object.class,
			String.class
	})
	@ParameterizedTest
	public void testResolveTypeArgumentsForNonGenericType(final Type clazz) {
		Assertions.assertNull(ParameterizedTypes.getTypeArgument(clazz, 0));
	}

}
