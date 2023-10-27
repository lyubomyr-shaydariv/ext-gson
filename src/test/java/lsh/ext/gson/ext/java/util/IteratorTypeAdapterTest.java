package lsh.ext.gson.ext.java.util;

import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.params.provider.Arguments;

public final class IteratorTypeAdapterTest
		extends AbstractTypeAdapterTest<Iterator<?>, List<?>> {

	private static final Gson gson = Gsons.getNormalized();

	@Nullable
	@Override
	protected List<?> normalize(@Nullable final Iterator<?> value) {
		return value != null ? ImmutableList.copyOf(value) : null;
	}

	@Override
	@SuppressWarnings("resource")
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						IteratorTypeAdapter.getInstance(gson.getAdapter(Integer.class)),
						"[1,2,4,8]",
						List.of(1, 2, 4, 8).iterator()
				)
		);
	}

}
