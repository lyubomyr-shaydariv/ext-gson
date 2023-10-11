package lsh.ext.gson.ext.java.time;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class YearMonthTypeAdapterTest
		extends AbstractTypeAdapterTest<YearMonth, YearMonth> {

	@Nullable
	@Override
	protected YearMonth normalize(@Nullable final YearMonth value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						YearMonthTypeAdapterFactory.Adapter.getInstance(),
						"\"1999-09\"",
						YearMonth.of(1999, 9)
				),
				makeTestCase(
						YearMonthTypeAdapterFactory.Adapter.getInstance(DateTimeFormatter.ofPattern("M.y")),
						"\"9.1999\"",
						YearMonth.of(1999, 9)
				)
		);
	}

}
