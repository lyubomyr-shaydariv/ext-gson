package lsh.ext.gson.adapters;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import lsh.ext.gson.CloseableIterators;
import lsh.ext.gson.ICloseableIterator;
import org.junit.jupiter.params.provider.Arguments;

public final class CloseableIteratorTypeAdapterTest
		extends AbstractTypeAdapterTest<ICloseableIterator<?>> {

	public CloseableIteratorTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final ICloseableIterator<?> value) {
		return ImmutableList.copyOf(value);
	}

	@Nonnull
	@Override
	@SuppressWarnings("resource")
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		return Stream.of(
				test(
						CloseableIteratorTypeAdapter.get(gson.getAdapter(Integer.class)),
						"[1,2,4,8]",
						() -> CloseableIterators.asCloseable(ImmutableList.of(1, 2, 4, 8).iterator())
				)
		);
	}

}
