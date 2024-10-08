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
	@SuppressWarnings("SimpleDateFormatWithoutLocale")
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(Java8TimeTypeAdapter.defaulFortMonthDay, "\"--07-13\"", MonthDay.of(Month.JULY, 13)),
				makeTestCase(Java8TimeTypeAdapter.forMonthDay(DateTimeFormatter.ofPattern("M.d")), "\"7.13\"", MonthDay.of(Month.JULY, 13))
		);
	}

}
