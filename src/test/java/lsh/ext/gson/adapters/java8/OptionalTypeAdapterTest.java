package lsh.ext.gson.adapters.java8;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
	@SuppressWarnings("TypeParameterExtendsFinalClass")
	public static Iterable<TestWith<? extends Optional<?>>> parameters() {
		return ImmutableList.of(
				parameterize(Optional.of("foo"), "\"foo\"")
						.with(TypeToken.get(String.class))
						.get()
		);
	}

	public OptionalTypeAdapterTest(@SuppressWarnings("TypeParameterExtendsFinalClass") final TestWith<? extends Optional<?>> testWith) {
		super(Optional.empty(), testWith);
	}

	@Nonnull
	@Override
	@SuppressWarnings("TypeParameterExtendsFinalClass")
	protected TypeAdapter<? extends Optional<?>> createDefaultUnit(@Nonnull final Gson gson, @Nullable final TypeToken<?> typeToken) {
		return OptionalTypeAdapter.get(gson.getAdapter(typeToken));
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Optional<?> value) {
		return value;
	}

}
