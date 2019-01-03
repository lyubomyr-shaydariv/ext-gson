package lsh.ext.gson.adapters.java8.time;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class YearMonthTypeAdapterTest
		extends AbstractTypeAdapterTest<YearMonth, YearMonth> {

	@Nullable
	@Override
	protected YearMonth finalize(@Nullable final YearMonth value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						YearMonthTypeAdapter.get(),
						"\"1999-09\"",
						() -> YearMonth.of(1999, 9)
				),
				test(
						YearMonthTypeAdapter.get(DateTimeFormatter.ofPattern("M.y")),
						"\"9.1999\"",
						() -> YearMonth.of(1999, 9)
				)
		);
	}

}
