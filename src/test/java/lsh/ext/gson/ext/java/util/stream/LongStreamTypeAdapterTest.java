package lsh.ext.gson.ext.java.util.stream;

import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractElementCursorTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class LongStreamTypeAdapterTest
		extends AbstractElementCursorTypeAdapterTest<LongStream> {

	@Override
	protected List<?> normalize(@Nullable final LongStream value) {
		return value != null ? value.boxed().toList() : List.of();
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						LongStreamTypeAdapter.getInstance(),
						"[1,2,4,8]",
						Stream.of(1L, 2L, 4L, 8L).mapToLong(value -> value)
				)
		);
	}

}
