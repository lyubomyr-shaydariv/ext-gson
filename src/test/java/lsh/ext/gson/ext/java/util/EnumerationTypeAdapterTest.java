package lsh.ext.gson.ext.java.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import lsh.ext.gson.AbstractCursorTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.params.provider.Arguments;

public final class EnumerationTypeAdapterTest
		extends AbstractCursorTypeAdapterTest<Enumeration<?>> {

	private static final Gson gson = Gsons.getNormalized();

	@Nullable
	@Override
	protected List<?> normalize(@Nullable final Enumeration<?> value) {
		return value != null ? List.copyOf(Collections.list(value)) : null;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						EnumerationTypeAdapter.getInstance(gson.getAdapter(Integer.class)),
						"[1,2,4,8]",
						Collections.enumeration(List.of(1, 2, 4, 8))
				)
		);
	}

}
