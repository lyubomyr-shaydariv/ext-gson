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
		final Throwable ex = Assertions.assertThrows(IllegalArgumentException.class, () -> ParameterizedTypes.getTypeArgument(Types.stringListTypeToken.getType(), 1));
		Assertions.assertEquals("1 is out of bounds for java.util.List<java.lang.String> that has 1 type argument(s)", ex.getMessage());
	}

	@Test
	public void testGetTypeArgumentForParameterizedTypeIsBelowZero() {
		final Throwable ex = Assertions.assertThrows(IllegalArgumentException.class, () -> ParameterizedTypes.getTypeArgument(Types.stringListTypeToken.getType(), -1));
		Assertions.assertEquals("-1 is out of bounds for java.util.List<java.lang.String> that has 1 type argument(s)", ex.getMessage());
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
