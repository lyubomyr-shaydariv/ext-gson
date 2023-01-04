package lsh.ext.gson.ext.java.time;

import java.time.Month;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MonthDayTypeAdapterTest
		extends AbstractTypeAdapterTest<MonthDay, MonthDay> {

	@Nullable
	@Override
	protected MonthDay normalize(@Nullable final MonthDay value) {
		return value;
	}

	@Override
	protected Stream<Arguments> makeTestCases() {
		return Stream.of(
				makeTestCase(
						MonthDayTypeAdapter.getInstance(),
						"\"--07-13\"",
						() -> MonthDay.of(Month.JULY, 13)
				),
				makeTestCase(
						MonthDayTypeAdapter.getInstance(DateTimeFormatter.ofPattern("M.d")),
						"\"7.13\"",
						() -> MonthDay.of(Month.JULY, 13)
				)
		);
	}

}
