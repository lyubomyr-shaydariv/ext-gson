package lsh.ext.gson.ext.java.time;

import java.time.DayOfWeek;
import java.util.List;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
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
		final TypeAdapter<DayOfWeek> unit = DayOfWeekTypeAdapter.getInstance();
		return List.of(
				makeTestCase(
						unit,
						"\"MONDAY\"",
						DayOfWeek.MONDAY
				),
				makeTestCase(
						unit,
						"\"FRIDAY\"",
						DayOfWeek.FRIDAY
				)
		);
	}

}
