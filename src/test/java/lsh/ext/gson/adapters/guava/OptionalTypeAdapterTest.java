package lsh.ext.gson.adapters.guava;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public final class OptionalTypeAdapterTest
		extends AbstractTypeAdapterTest<Optional<?>> {

	@Parameterized.Parameters
	public static Iterable<TestWith<Optional<?>>> parameters() {
		return ImmutableList.of(
				testWith(
						TypeToken.get(String.class),
						Optional.of("foo"),
						"\"foo\""
				)
		);
	}

	public OptionalTypeAdapterTest(final TestWith<? extends Optional<?>> testWith) {
		super(Optional.absent(), testWith);
	}

	@Nonnull
	@Override
	protected TypeAdapter<? extends Optional<?>> createUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return OptionalTypeAdapter.get(gson.getAdapter(typeToken));
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Optional<?> value) {
		return value;
	}

}
