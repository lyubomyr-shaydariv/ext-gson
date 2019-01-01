package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractTypeAdapterTest<T> {

	private final T nullValue;

	protected AbstractTypeAdapterTest(final T nullValue) {
		this.nullValue = nullValue;
	}

	@Nonnull
	protected abstract Stream<Arguments> source();

	@Nonnull
	protected abstract Object finalizeValue(@Nonnull T value);

	@ParameterizedTest
	@MethodSource("source")
	public final void testRead(final TypeAdapter<T> unit, final String json, final Supplier<? extends T> valueFactory)
			throws IOException {
		Assertions.assertEquals(finalizeValue(valueFactory.get()), finalizeValue(unit.fromJson(json)));
	}

	@ParameterizedTest
	@MethodSource("source")
	public final void testReadNull(final TypeAdapter<T> unit, @SuppressWarnings("unused") final String json,
			@SuppressWarnings("unused") final Supplier<? extends T> valueFactory)
			throws IOException {
		Assertions.assertEquals(nullValue, unit.fromJson("null"));
	}

	@ParameterizedTest
	@MethodSource("source")
	public final void testWrite(final TypeAdapter<T> unit, final String json, final Supplier<? extends T> valueFactory) {
		Assertions.assertEquals(json, unit.toJson(valueFactory.get()));
	}

	@ParameterizedTest
	@MethodSource("source")
	public final void testWriteNull(final TypeAdapter<T> unit, @SuppressWarnings("unused") final String json,
			@SuppressWarnings("unused") final Supplier<? extends T> valueFactory) {
		Assertions.assertEquals("null", unit.toJson(nullValue));
	}

}
