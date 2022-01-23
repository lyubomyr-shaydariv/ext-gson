package lsh.ext.gson.ext.java.util.stream;

import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class StreamTypeAdapterTest
		extends AbstractTypeAdapterTest<Stream<?>, List<?>> {

	@Nullable
	@Override
	protected List<?> finalize(@Nullable final Stream<?> value) {
		return value != null ? value.collect(ImmutableList.toImmutableList()) : null;
	}

	@Override
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		return Stream.of(
				test(
						StreamTypeAdapter.create(gson.getAdapter(Integer.class)),
						"[1,2,4,8]",
						() -> ImmutableList.of(1, 2, 4, 8).stream()
				)
		);
	}

}
