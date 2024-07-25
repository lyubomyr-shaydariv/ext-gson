package lsh.ext.gson.ext.java.util.stream;

import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractCursorTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class DoubleStreamTypeAdapterTest
		extends AbstractCursorTypeAdapterTest<DoubleStream> {

	@Override
	protected List<?> normalize(@Nullable final DoubleStream value) {
		return value != null ? value.boxed().toList() : List.of();
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						DoubleStreamTypeAdapter.getInstance(),
						"[1,2,4,8]",
						"[1.0,2.0,4.0,8.0]",
						Stream.of(1.0D, 2.0D, 4.0D, 8.0D).mapToDouble(value -> value)
				)
		);
	}

}
