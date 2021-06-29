package lsh.ext.gson.ext.java.time;

import java.time.Month;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MonthDayTypeAdapterTest
		extends AbstractTypeAdapterTest<MonthDay, MonthDay> {

	@Nullable
	@Override
	protected MonthDay finalize(@Nullable final MonthDay value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						MonthDayTypeAdapter.getDefaultInstance(),
						"\"--07-13\"",
						() -> MonthDay.of(Month.JULY, 13)
				),
				test(
						MonthDayTypeAdapter.create(DateTimeFormatter.ofPattern("M.d")),
						"\"7.13\"",
						() -> MonthDay.of(Month.JULY, 13)
				)
		);
	}

}
