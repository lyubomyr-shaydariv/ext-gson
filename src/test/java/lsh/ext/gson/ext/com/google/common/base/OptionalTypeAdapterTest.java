package lsh.ext.gson.ext.com.google.common.base;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.base.Optional;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.test.TestTypeAdapters;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalTypeAdapterTest
		extends AbstractTypeAdapterTest<Optional<?>, Optional<?>> {

	@Override
	@SuppressWarnings("OptionalAssignedToNull")
	protected Optional<?> normalize(@Nullable final Optional<?> value) {
		return value != null ? value : Optional.absent();
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						OptionalTypeAdapter.getInstance(TestTypeAdapters.stringTypeAdapter),
						"\"foo\"",
						Optional.of("foo")
				)
		);
	}

}
