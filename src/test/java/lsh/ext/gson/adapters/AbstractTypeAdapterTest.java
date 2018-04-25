package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.function.BiFunction;
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

	protected static final class TestWith<T> {

		@Nonnull
		private final Supplier<? extends T> valueFactory;

		@Nonnull
		private final String json;

		@Nullable
		private final BiFunction<? super Gson, ? super TypeToken<?>, ? extends TypeAdapter<?>> unitFactory;

		@Nullable
		private final TypeToken<?> typeToken;

		private final boolean withoutWrite;

		private TestWith(@Nonnull final Supplier<? extends T> valueFactory, @Nonnull final String json,
				@Nullable final BiFunction<? super Gson, ? super TypeToken<?>, ? extends TypeAdapter<?>> unitFactory, @Nullable final TypeToken<?> typeToken,
				final boolean withoutWrite) {
			this.valueFactory = valueFactory;
			this.json = json;
			this.unitFactory = unitFactory;
			this.typeToken = typeToken;
			this.withoutWrite = withoutWrite;
		}

		public static final class Builder<T>
				implements Supplier<TestWith<T>> {

			@Nonnull
			private final Supplier<? extends T> valueFactory;

			@Nonnull
			private final String json;

			@Nullable
			private BiFunction<? super Gson, ? super TypeToken<?>, ? extends TypeAdapter<?>> unitFactory;

			@Nullable
			private TypeToken<?> typeToken;

			private boolean withoutRead;

			private boolean withoutWrite;

			private Builder(@Nonnull final Supplier<? extends T> valueFactory, @Nonnull final String json) {
				this.valueFactory = valueFactory;
				this.json = json;
			}

			public Builder<T> with(final TypeToken<?> typeToken) {
				this.typeToken = typeToken;
				return this;
			}

			@SuppressWarnings("unchecked")
			public <U> Builder<T> with(final BiFunction<? super Gson, ? super TypeToken<U>, ? extends TypeAdapter<U>> unitFactory) {
				this.unitFactory = (BiFunction<? super Gson, ? super TypeToken<?>, ? extends TypeAdapter<?>>) unitFactory;
				return this;
			}

			public Builder<T> withoutWrite() {
				withoutWrite = true;
				return this;
			}

			@Override
			public TestWith<T> get() {
				return new TestWith<>(valueFactory, json, unitFactory, typeToken, withoutWrite);
			}

		}

	}

	private static final Gson gson = new Gson();

	private final T nullValue;
	private final TestWith<? extends T> testWith;

	protected AbstractTypeAdapterTest(final T nullValue, final TestWith<? extends T> testWith) {
		this.nullValue = nullValue;
		this.testWith = testWith;
	}

	protected static <T> TestWith.Builder<T> parameterize(final T value, final String json) {
		return new TestWith.Builder<T>(() -> value, json);
	}

	protected static <T> TestWith.Builder<T> parameterize(final Supplier<? extends T> valueFactory, final String json) {
		return new TestWith.Builder<T>(valueFactory, json);
	}

	@Nonnull
	protected abstract TypeAdapter<? extends T> createDefaultUnit(@Nonnull Gson gson, @Nullable TypeToken<?> typeToken);

	@Nonnull
	protected abstract Object finalizeValue(@Nonnull T value);

	@Test
	public final void testRead()
			throws IOException {
		final TypeAdapter<T> unit = createUnit(testWith, gson);
		MatcherAssert.assertThat(finalizeValue(unit.fromJson(testWith.json)), CoreMatchers.is(finalizeValue(testWith.valueFactory.get())));
	}

	@Test
	public final void testReadNull()
			throws IOException {
		final TypeAdapter<T> unit = createUnit(testWith, gson);
		MatcherAssert.assertThat(unit.fromJson("null"), CoreMatchers.is(nullValue));
	}

	@Test
	public final void testWrite() {
		if ( !testWith.withoutWrite ) {
			final TypeAdapter<T> unit = createUnit(testWith, gson);
			MatcherAssert.assertThat(unit.toJson(testWith.valueFactory.get()), CoreMatchers.is(testWith.json));
		}
	}

	@Test
	public final void testWriteNull() {
		final TypeAdapter<T> unit = createUnit(testWith, gson);
		MatcherAssert.assertThat(unit.toJson(nullValue), CoreMatchers.is("null"));
	}

	private TypeAdapter<T> createUnit(final TestWith<? extends T> testWith, final Gson gson) {
		final TypeAdapter<?> typeAdapter = testWith.unitFactory == null
				? createDefaultUnit(gson, testWith.typeToken)
				: testWith.unitFactory.apply(gson, testWith.typeToken);
		@SuppressWarnings("unchecked")
		final TypeAdapter<T> castTypeAdapter = (TypeAdapter<T>) typeAdapter;
		return castTypeAdapter;
	}

}
