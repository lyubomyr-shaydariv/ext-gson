package lsh.ext.gson.ext.java.util;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.test.TypeAdapters;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalTypeAdapterTest
		extends AbstractTypeAdapterTest<Optional<?>, Optional<?>> {

	@Override
	@SuppressWarnings("OptionalAssignedToNull")
	protected Optional<?> normalize(@Nullable final Optional<?> value) {
		return value != null ? value : Optional.empty();
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						OptionalTypeAdapter.getInstance(TypeAdapters.stringTypeAdapter),
						"\"foo\"",
						Optional.of("foo")
				)
		);
	}

}
