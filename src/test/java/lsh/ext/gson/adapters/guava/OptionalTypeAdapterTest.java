package lsh.ext.gson.adapters.guava;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.gson.Gson;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalTypeAdapterTest
		extends AbstractTypeAdapterTest<Optional<?>> {

	public OptionalTypeAdapterTest() {
		super(Optional.absent());
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Optional<?> value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		return Stream.of(
				Arguments.of(OptionalTypeAdapter.get(gson.getAdapter(String.class)), "\"foo\"", (Supplier<?>) () -> Optional.of("foo"))
		);
	}

}
