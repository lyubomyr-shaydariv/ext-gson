package lsh.ext.gson.ext.java;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import lsh.ext.gson.GsonBuilders;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class CloseableIteratorTypeAdapterTest
		extends AbstractTypeAdapterTest<ICloseableIterator<?>, List<?>> {

	@Nullable
	@Override
	protected List<?> normalize(@Nullable final ICloseableIterator<?> value) {
		return value != null ? ImmutableList.copyOf(value) : null;
	}

	@Override
	@SuppressWarnings("resource")
	protected List<Arguments> makeTestCases() {
		final Gson gson = GsonBuilders.createNormalized()
				.create();
		return List.of(
				makeTestCase(
						CloseableIteratorTypeAdapter.getInstance(gson.getAdapter(Integer.class)),
						"[1,2,4,8]",
						CloseableIterators.asCloseable(ImmutableList.of(1, 2, 4, 8).iterator())
				)
		);
	}

}
