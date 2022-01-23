package lsh.ext.gson.ext.java.time;

import java.time.Month;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MonthTypeAdapterTest
		extends AbstractTypeAdapterTest<Month, Month> {

	@Nullable
	@Override
	protected Month finalize(@Nullable final Month value) {
		return value;
	}

	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						MonthTypeAdapter.getInstance(),
						"\"SEPTEMBER\"",
						() -> Month.SEPTEMBER
				)
		);
	}

}
