package lsh.ext.gson.ext.java.time;

import java.time.Month;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MonthDayTypeAdapterTest
		extends AbstractTypeAdapterTest<MonthDay, MonthDay> {

	@Nullable
	@Override
	protected MonthDay normalize(@Nullable final MonthDay value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						MonthDayTypeAdapterFactory.Adapter.getInstance(),
						"\"--07-13\"",
						MonthDay.of(Month.JULY, 13)
				),
				makeTestCase(
						MonthDayTypeAdapterFactory.Adapter.getInstance(DateTimeFormatter.ofPattern("M.d")),
						"\"7.13\"",
						MonthDay.of(Month.JULY, 13)
				)
		);
	}

}
