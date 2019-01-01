package lsh.ext.gson.adapters;

import java.lang.reflect.Type;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractSimpleTest {

	private static final Gson gson = new Gson();

	@Nonnull
	protected abstract Stream<Arguments> source();

	@ParameterizedTest
	@MethodSource("source")
	public final void testRead(final Type type, final String json, final Object value) {
		Assertions.assertEquals(value, gson.fromJson(json, type));
	}

	@ParameterizedTest
	@MethodSource("source")
	public final void testWrite(final Type type, final String json, final Object value) {
		Assertions.assertEquals(json, gson.toJson(value, type));
	}

}
