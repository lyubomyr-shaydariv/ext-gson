package lsh.ext.gson.ext.com.google.common.base;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalTypeAdapterTest
		extends AbstractTypeAdapterTest<Optional<?>, Optional<?>> {

	private static final Gson gson = Gsons.getNormalized();

	@Override
	@SuppressWarnings("OptionalAssignedToNull")
	protected Optional<?> normalize(@Nullable final Optional<?> value) {
		return value != null ? value : Optional.absent();
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						OptionalTypeAdapter.getInstance(gson.getAdapter(String.class)),
						"\"foo\"",
						Optional.of("foo")
				)
		);
	}

}
