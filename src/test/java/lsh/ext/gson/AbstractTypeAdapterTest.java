package lsh.ext.gson;

import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTypeAdapterTest<T, R> {

	protected abstract List<Arguments> makeTestCases();

	@Nullable
	protected abstract R normalize(@Nullable T value);

	@ParameterizedTest
	@MethodSource("makeTestCases")
	public final void testRead(final TypeAdapter<? extends T> unit, final String readJson, @SuppressWarnings("unused") final String writeJson, final T value)
			throws IOException {
		Assertions.assertEquals(normalize(value), normalize(unit.fromJson(readJson)));
	}

	@ParameterizedTest
	@MethodSource("makeTestCases")
	public final void testWrite(final TypeAdapter<? super T> unit, @SuppressWarnings("unused") final String readJson, final String writeJson, final T value) {
		Assertions.assertEquals(writeJson, unit.toJson(value));
	}

	protected final Arguments makeTestCase(final TypeAdapter<?> unit, final String json, final T value) {
		return makeTestCase(unit, json, json, value);
	}

	protected final Arguments makeTestCase(final TypeAdapter<?> unit, final String readJson, final String writeJson, final T value) {
		return Arguments.of(unit, readJson, writeJson, value);
	}

}
