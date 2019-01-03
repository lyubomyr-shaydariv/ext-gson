package lsh.ext.gson.adapters.java8.time;

import java.time.Month;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MonthDayTypeAdapterTest
		extends AbstractTypeAdapterTest<MonthDay> {

	public MonthDayTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final MonthDay value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						MonthDayTypeAdapter.get(),
						"\"--07-13\"",
						() -> MonthDay.of(Month.JULY, 13)
				),
				test(
						MonthDayTypeAdapter.get(DateTimeFormatter.ofPattern("M.d")),
						"\"7.13\"",
						() -> MonthDay.of(Month.JULY, 13)
				)
		);
	}

}
