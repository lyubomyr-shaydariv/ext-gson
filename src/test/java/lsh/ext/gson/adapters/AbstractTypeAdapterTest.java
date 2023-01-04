package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTypeAdapterTest<T, R> {

	protected abstract Stream<Arguments> makeTestCases();

	@Nullable
	protected abstract R normalize(@Nullable T value);

	@ParameterizedTest
	@MethodSource("makeTestCases")
	public final void testRead(final TypeAdapter<? extends T> unit, final String readJson, @SuppressWarnings("unused") final String writeJson, final Supplier<? extends T> valueFactory)
			throws IOException {
		Assertions.assertEquals(normalize(valueFactory.get()), normalize(unit.fromJson(readJson)));
	}

	@ParameterizedTest
	@MethodSource("makeTestCases")
	public final void testReadNull(final TypeAdapter<? super T> unit, @SuppressWarnings("unused") final String readJson, @SuppressWarnings("unused") final String writeJson,
			@SuppressWarnings("unused") final Supplier<? extends T> valueFactory)
			throws IOException {
		Assertions.assertEquals(normalize(null), unit.fromJson("null"));
	}

	@ParameterizedTest
	@MethodSource("makeTestCases")
	public final void testWrite(final TypeAdapter<? super T> unit, @SuppressWarnings("unused") final String readJson, final String writeJson, final Supplier<? extends T> valueFactory) {
		Assertions.assertEquals(writeJson, unit.toJson(valueFactory.get()));
	}

	@ParameterizedTest
	@MethodSource("makeTestCases")
	public final void testWriteNull(final TypeAdapter<? super T> unit, @SuppressWarnings("unused") final String readJson, @SuppressWarnings("unused") final String writeJson,
			@SuppressWarnings("unused") final Supplier<? extends T> valueFactory) {
		@SuppressWarnings("unchecked")
		final T finalize = (T) normalize(null);
		Assertions.assertEquals("null", unit.toJson(finalize));
	}

	protected static Arguments makeTestCase(final TypeAdapter<?> unit, final String json, final Supplier<?> valueFactory) {
		return makeTestCase(unit, json, json, valueFactory);
	}

	protected static Arguments makeTestCase(final TypeAdapter<?> unit, final String readJson, final String writeJson, final Supplier<?> valueFactory) {
		return Arguments.of(unit, readJson, writeJson, valueFactory);
	}

}
