package lsh.ext.gson.ext.java.time;

import java.time.DayOfWeek;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class DayOfWeekTypeAdapterTest
		extends AbstractTypeAdapterTest<DayOfWeek, DayOfWeek> {

	@Nullable
	@Override
	protected DayOfWeek normalize(@Nullable final DayOfWeek value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						DayOfWeekTypeAdapterFactory.Adapter.getInstance(),
						"\"MONDAY\"",
						DayOfWeek.MONDAY
				),
				makeTestCase(
						DayOfWeekTypeAdapterFactory.Adapter.getInstance(),
						"\"FRIDAY\"",
						DayOfWeek.FRIDAY
				)
		);
	}

}
