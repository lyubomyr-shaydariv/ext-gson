package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.gson.TypeAdapter;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
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
		MatcherAssert.assertThat(finalizeValue(unit.fromJson(json)), CoreMatchers.is(finalizeValue(valueFactory.get())));
	}

	@ParameterizedTest
	@MethodSource("source")
	public final void testReadNull(final TypeAdapter<T> unit, @SuppressWarnings("unused") final String json,
			@SuppressWarnings("unused") final Supplier<? extends T> valueFactory)
			throws IOException {
		MatcherAssert.assertThat(unit.fromJson("null"), CoreMatchers.is(nullValue));
	}

	@ParameterizedTest
	@MethodSource("source")
	public final void testWrite(final TypeAdapter<T> unit, final String json, final Supplier<? extends T> valueFactory) {
		MatcherAssert.assertThat(unit.toJson(valueFactory.get()), CoreMatchers.is(json));
	}

	@ParameterizedTest
	@MethodSource("source")
	public final void testWriteNull(final TypeAdapter<T> unit, @SuppressWarnings("unused") final String json,
			@SuppressWarnings("unused") final Supplier<? extends T> valueFactory) {
		MatcherAssert.assertThat(unit.toJson(nullValue), CoreMatchers.is("null"));
	}

}
