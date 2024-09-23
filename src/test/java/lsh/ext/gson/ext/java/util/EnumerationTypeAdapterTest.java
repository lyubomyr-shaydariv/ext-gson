package lsh.ext.gson.ext.java.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractElementCursorTypeAdapterTest;
import lsh.ext.gson.test.TestTypeAdapters;
import org.junit.jupiter.params.provider.Arguments;

public final class EnumerationTypeAdapterTest
		extends AbstractElementCursorTypeAdapterTest<Enumeration<?>> {

	@Nullable
	@Override
	protected List<?> normalize(@Nullable final Enumeration<?> value) {
		return value != null ? List.copyOf(Collections.list(value)) : null;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						UtilTypeAdapter.forEnumeration(TestTypeAdapters.integerTypeAdapter, false),
						"[1,2,4,8]",
						Collections.enumeration(List.of(1, 2, 4, 8))
				)
		);
	}

}
