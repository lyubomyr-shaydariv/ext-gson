package lsh.ext.gson.ext.java.time;

import java.time.DayOfWeek;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class DayOfWeekTypeAdapterTest
		extends AbstractTypeAdapterTest<DayOfWeek, DayOfWeek> {

	@Nullable
	@Override
	protected DayOfWeek normalize(@Nullable final DayOfWeek value) {
		return value;
	}

	@Override
	protected Stream<Arguments> makeTestCases() {
		return Stream.of(
				makeTestCase(
						DayOfWeekTypeAdapter.getInstance(),
						"\"MONDAY\"",
						DayOfWeek.MONDAY
				),
				makeTestCase(
						DayOfWeekTypeAdapter.getInstance(),
						"\"FRIDAY\"",
						DayOfWeek.FRIDAY
				)
		);
	}

}
