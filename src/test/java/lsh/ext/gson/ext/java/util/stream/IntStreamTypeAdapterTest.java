package lsh.ext.gson.ext.java.util.stream;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractCursorTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class IntStreamTypeAdapterTest
		extends AbstractCursorTypeAdapterTest<IntStream> {

	@Override
	protected List<?> normalize(@Nullable final IntStream value) {
		return value != null ? value.boxed().toList() : List.of();
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						IntStreamTypeAdapter.getInstance(),
						"[1,2,4,8]",
						Stream.of(1, 2, 4, 8).mapToInt(value -> value)
				)
		);
	}

}
