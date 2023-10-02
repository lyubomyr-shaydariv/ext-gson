package lsh.ext.gson.ext.java.util;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import lsh.ext.gson.GsonBuilders;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalTypeAdapterTest
		extends AbstractTypeAdapterTest<Optional<?>, Optional<?>> {

	@Nullable
	@Override
	protected Optional<?> normalize(@Nullable final Optional<?> value) {
		return value != null ? value : Optional.empty();
	}

	@Override
	protected List<Arguments> makeTestCases() {
		final Gson gson = GsonBuilders.createNormalized()
				.create();
		return List.of(
				makeTestCase(
						OptionalTypeAdapter.getInstance(gson.getAdapter(String.class)),
						"\"foo\"",
						Optional.of("foo")
				)
		);
	}

}
