package lsh.ext.gson.adapters;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import lsh.ext.gson.CloseableIterators;
import lsh.ext.gson.ICloseableIterator;
import org.junit.jupiter.params.provider.Arguments;

public final class CloseableIteratorTypeAdapterTest
		extends AbstractTypeAdapterTest<ICloseableIterator<?>, List<?>> {

	@Nullable
	@Override
	protected List<?> finalize(@Nullable final ICloseableIterator<?> value) {
		return value != null ? ImmutableList.copyOf(value) : null;
	}

	@Override
	@SuppressWarnings("resource")
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		return Stream.of(
				test(
						CloseableIteratorTypeAdapter.getInstance(gson.getAdapter(Integer.class)),
						"[1,2,4,8]",
						() -> CloseableIterators.asCloseable(ImmutableList.of(1, 2, 4, 8).iterator())
				)
		);
	}

}
