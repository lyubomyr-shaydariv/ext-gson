package lsh.ext.gson.ext.java.util;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.Gsons;
import org.junit.jupiter.params.provider.Arguments;

public final class OptionalTypeAdapterTest
		extends AbstractTypeAdapterTest<Optional<?>, Optional<?>> {

	private static final Gson gson = Gsons.getNormalized();

	@Nullable
	@Override
	protected Optional<?> normalize(@Nullable final Optional<?> value) {
		return value != null ? value : Optional.empty();
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						OptionalTypeAdapterFactory.Adapter.getInstance(gson.getAdapter(String.class)),
						"\"foo\"",
						Optional.of("foo")
				)
		);
	}

}
