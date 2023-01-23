package lsh.ext.gson.ext.java.util.stream;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import lsh.ext.gson.GsonBuilders;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class StreamTypeAdapterTest
		extends AbstractTypeAdapterTest<Stream<?>, List<?>> {

	@Nullable
	@Override
	protected List<?> normalize(@Nullable final Stream<?> value) {
		return value != null ? value.toList() : null;
	}

	@Override
	protected Stream<Arguments> makeTestCases() {
		final Gson gson = GsonBuilders.createCanonical()
				.create();
		return Stream.of(
				makeTestCase(
						StreamTypeAdapter.getInstance(gson.getAdapter(Integer.class)),
						"[1,2,4,8]",
						Stream.of(1, 2, 4, 8)
				)
		);
	}

}
