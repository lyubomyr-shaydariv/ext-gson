package lsh.ext.gson;

import java.lang.reflect.Type;

import lsh.ext.gson.test.Types;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public final class ParameterizedTypesTest {

	@Test
	public void testGetTypeArgumentForParameterizedType() {
		Assertions.assertEquals(String.class, ParameterizedTypes.getTypeArgument(Types.stringListTypeToken.getType(), 0));
	}

	@Test
	public void testGetTypeArgumentForParameterizedTypeGoesBeyond() {
		Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> ParameterizedTypes.getTypeArgument(Types.stringListTypeToken.getType(), 1));
	}

	@ValueSource(classes = {
			void.class,
			int.class,
			Object.class,
			String.class
	})
	@ParameterizedTest
	public void testResolveTypeArgumentsForNonGenericType(final Type klass) {
		Assertions.assertNull(ParameterizedTypes.getTypeArgument(klass, 0));
	}

}
