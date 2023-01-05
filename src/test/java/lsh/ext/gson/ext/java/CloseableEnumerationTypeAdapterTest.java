package lsh.ext.gson.ext.java;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import lsh.ext.gson.GsonBuilders;
import lsh.ext.gson.ICloseableEnumeration;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class CloseableEnumerationTypeAdapterTest
		extends AbstractTypeAdapterTest<ICloseableEnumeration<?>, List<?>> {

	@Nullable
	@Override
	protected List<?> normalize(@Nullable final ICloseableEnumeration<?> value) {
		return value != null ? ImmutableList.copyOf(Collections.list(value)) : null;
	}

	@Override
	@SuppressWarnings("resource")
	protected Stream<Arguments> makeTestCases() {
		final Gson gson = GsonBuilders.createCanonical()
				.create();
		return Stream.of(
				makeTestCase(
						CloseableEnumerationTypeAdapter.getInstance(gson.getAdapter(Integer.class)),
						"[1,2,4,8]",
						CloseableEnumerations.from(CloseableIterators.asCloseable(ImmutableList.of(1, 2, 4, 8).iterator()))
				)
		);
	}

}
