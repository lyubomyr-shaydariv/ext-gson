package lsh.ext.gson;

import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public final class JsonPrimitivesTest {

	@ParameterizedTest
	@ValueSource(booleans = { false, true })
	public void testJsonPrimitiveForBooleans(final boolean b) {
		Assertions.assertEquals(new JsonPrimitive(b), JsonPrimitives.of(b));
	}

	@ParameterizedTest
	@ValueSource(booleans = { false, true })
	public void testJsonPrimitiveForBooleansMustReturnFlyweights(final boolean b) {
		final JsonPrimitive before = JsonPrimitives.of(b);
		final JsonPrimitive after = JsonPrimitives.of(b);
		Assertions.assertSame(before, after);
	}

}
