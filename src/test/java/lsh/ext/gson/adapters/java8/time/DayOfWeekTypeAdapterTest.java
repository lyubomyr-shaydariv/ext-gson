package lsh.ext.gson.adapters.java8.time;

import java.time.DayOfWeek;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class DayOfWeekTypeAdapterTest
		extends AbstractTypeAdapterTest<DayOfWeek, DayOfWeek> {

	@Nullable
	@Override
	protected DayOfWeek finalize(@Nullable final DayOfWeek value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						DayOfWeekTypeAdapter.get(),
						"\"MONDAY\"",
						() -> DayOfWeek.MONDAY
				),
				test(
						DayOfWeekTypeAdapter.get(),
						"\"FRIDAY\"",
						() -> DayOfWeek.FRIDAY
				)
		);
	}

}
