package lsh.ext.gson.adapters.java8;

import java.util.stream.Stream;
import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class StreamTypeAdapterTest
		extends AbstractTypeAdapterTest<Stream<?>> {

	public StreamTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Stream<?> value) {
		return value.collect(ImmutableList.toImmutableList());
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		final Gson gson = new Gson();
		return Stream.of(
				test(
						StreamTypeAdapter.get(gson.getAdapter(Integer.class)),
						"[1,2,4,8]",
						() -> ImmutableList.of(1, 2, 4, 8).stream()
				)
		);
	}

}
