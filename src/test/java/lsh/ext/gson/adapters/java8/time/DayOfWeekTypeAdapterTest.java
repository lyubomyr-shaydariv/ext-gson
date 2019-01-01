package lsh.ext.gson.adapters.java8.time;

import java.time.DayOfWeek;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class DayOfWeekTypeAdapterTest
		extends AbstractTypeAdapterTest<DayOfWeek> {

	public DayOfWeekTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final DayOfWeek value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				Arguments.of(DayOfWeekTypeAdapter.get(), "\"MONDAY\"", (Supplier<?>) () -> DayOfWeek.MONDAY),
				Arguments.of(DayOfWeekTypeAdapter.get(), "\"FRIDAY\"", (Supplier<?>) () -> DayOfWeek.FRIDAY)
		);
	}

}
