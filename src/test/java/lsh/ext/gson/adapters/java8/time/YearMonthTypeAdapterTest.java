package lsh.ext.gson.adapters.java8.time;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class YearMonthTypeAdapterTest
		extends AbstractTypeAdapterTest<YearMonth> {

	public YearMonthTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final YearMonth value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				Arguments.of(YearMonthTypeAdapter.get(), "\"1999-09\"", (Supplier<?>) () -> YearMonth.of(1999, 9)),
				Arguments.of(YearMonthTypeAdapter.get(DateTimeFormatter.ofPattern("M.y")), "\"9.1999\"", (Supplier<?>) () -> YearMonth.of(1999, 9))
		);
	}

}
