package lsh.ext.gson.ext.java;

import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.params.provider.Arguments;

public final class CloseableEnumerationTypeAdapterTest
		extends AbstractTypeAdapterTest<ICloseableEnumeration<?>, List<?>> {

	private static final Gson gson = Gsons.getNormalized();

	@Nullable
	@Override
	protected List<?> normalize(@Nullable final ICloseableEnumeration<?> value) {
		return value != null ? List.copyOf(Collections.list(value)) : null;
	}

	@Override
	@SuppressWarnings("resource")
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						CloseableEnumerationTypeAdapter.getInstance(gson.getAdapter(Integer.class)),
						"[1,2,4,8]",
						CloseableEnumerations.from(CloseableIterators.asCloseable(List.of(1, 2, 4, 8).iterator()))
				)
		);
	}

}
