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

	protected abstract Stream<Arguments> source();

	@Nullable
	protected abstract R finalize(@Nullable T value);

	@ParameterizedTest
	@MethodSource("source")
	public final void testRead(final TypeAdapter<T> unit, final Json json, final Supplier<? extends T> valueFactory)
			throws IOException {
		Assertions.assertEquals(finalize(valueFactory.get()), finalize(unit.fromJson(json.read)));
	}

	@ParameterizedTest
	@MethodSource("source")
	public final void testReadNull(final TypeAdapter<T> unit, @SuppressWarnings("unused") final Json json,
			@SuppressWarnings("unused") final Supplier<? extends T> valueFactory)
			throws IOException {
		Assertions.assertEquals(finalize(null), unit.fromJson("null"));
	}

	@ParameterizedTest
	@MethodSource("source")
	public final void testWrite(final TypeAdapter<T> unit, final Json json, final Supplier<? extends T> valueFactory) {
		Assertions.assertEquals(json.write, unit.toJson(valueFactory.get()));
	}

	@ParameterizedTest
	@MethodSource("source")
	public final void testWriteNull(final TypeAdapter<T> unit, @SuppressWarnings("unused") final Json json,
			@SuppressWarnings("unused") final Supplier<? extends T> valueFactory) {
		@SuppressWarnings("unchecked")
		final T finalize = (T) finalize(null);
		Assertions.assertEquals("null", unit.toJson(finalize));
	}

	protected static Arguments test(final TypeAdapter<?> unit, final String json, final Supplier<?> valueFactory) {
		return Arguments.of(unit, new Json(json, json), valueFactory);
	}

	protected static Arguments test(final TypeAdapter<?> unit, final String readJson, final String writeJson, final Supplier<?> valueFactory) {
		return Arguments.of(unit, new Json(readJson, writeJson), valueFactory);
	}

	private static final class Json {

		private final String read;
		private final String write;

		private Json(final String read, final String write) {
			this.read = read;
			this.write = write;
		}

	}

}
