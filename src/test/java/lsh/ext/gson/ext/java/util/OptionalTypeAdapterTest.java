package lsh.ext.gson.ext.java.util;

import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalTypeAdapterTest
		extends AbstractTypeAdapterTest<Optional<?>, Optional<?>> {

	@Nullable
	@Override
	protected Optional<?> finalize(@Nullable final Optional<?> value) {
		return value != null ? value : Optional.empty();
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		return Stream.of(
				test(
						OptionalTypeAdapter.create(gson.getAdapter(String.class)),
						"\"foo\"",
						() -> Optional.of("foo")
				)
		);
	}

}
