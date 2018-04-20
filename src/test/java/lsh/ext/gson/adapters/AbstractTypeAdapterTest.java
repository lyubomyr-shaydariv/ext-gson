package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public abstract class AbstractTypeAdapterTest<T> {

	public static final class TestWith<T> {

		@Nullable
		private final TypeToken<?> typeToken;

		@Nonnull
		private final Supplier<? extends T> valueFactory;

		@Nonnull
		private final String json;

		private TestWith(@Nullable final TypeToken<?> typeToken, @Nonnull final Supplier<? extends T> valueFactory, @Nonnull final String json) {
			this.typeToken = typeToken;
			this.valueFactory = valueFactory;
			this.json = json;
		}

	}

	private static final Gson gson = new Gson();

	private final T nullValue;
	private final TestWith<? extends T> testWith;

	protected AbstractTypeAdapterTest(final T nullValue, final TestWith<? extends T> testWith) {
		this.nullValue = nullValue;
		this.testWith = testWith;
	}

	protected static <T> TestWith<T> testWith(@Nonnull final Supplier<? extends T> valueFactory, @Nonnull final String json) {
		return new TestWith<>(null, valueFactory, json);
	}

	protected static <T> TestWith<T> testWith(@Nonnull final TypeToken<?> typeToken, @Nonnull final Supplier<? extends T> valueFactory,
			@Nonnull final String json) {
		return new TestWith<>(typeToken, valueFactory, json);
	}

	protected static <T> TestWith<T> testWith(@Nonnull final T value, @Nonnull final String json) {
		return new TestWith<>(null, () -> value, json);
	}

	protected static <T> TestWith<T> testWith(@Nonnull final TypeToken<?> typeToken, @Nonnull final T value, @Nonnull final String json) {
		return new TestWith<>(typeToken, () -> value, json);
	}

	@Nonnull
	protected abstract TypeAdapter<? extends T> createUnit(@Nonnull Gson gson, @Nullable TypeToken<?> typeToken);

	@Nonnull
	protected abstract Object finalizeValue(@Nonnull T value);

	@Test
	public final void testWrite()
			throws IOException {
		final TypeAdapter<? extends T> unit = createUnit(gson, testWith.typeToken);
		MatcherAssert.assertThat(finalizeValue(unit.fromJson(testWith.json)), CoreMatchers.is(finalizeValue(testWith.valueFactory.get())));
	}

	@Test
	public final void testWriteNull()
			throws IOException {
		final TypeAdapter<? extends T> unit = createUnit(gson, testWith.typeToken);
		MatcherAssert.assertThat(unit.fromJson("null"), CoreMatchers.is(nullValue));
	}

	@Test
	public final void testRead() {
		@SuppressWarnings("unchecked")
		final TypeAdapter<T> unit = (TypeAdapter<T>) createUnit(gson, testWith.typeToken);
		MatcherAssert.assertThat(unit.toJson(testWith.valueFactory.get()), CoreMatchers.is(testWith.json));
	}

	@Test
	public final void testReadNull() {
		@SuppressWarnings("unchecked")
		final TypeAdapter<T> unit = (TypeAdapter<T>) createUnit(gson, testWith.typeToken);
		MatcherAssert.assertThat(unit.toJson(nullValue), CoreMatchers.is("null"));
	}

}
