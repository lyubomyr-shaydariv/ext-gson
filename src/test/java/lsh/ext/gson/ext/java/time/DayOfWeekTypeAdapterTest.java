package lsh.ext.gson.ext.java.time;

import java.time.DayOfWeek;
import java.util.stream.Stream;
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

	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						DayOfWeekTypeAdapter.getDefaultInstance(),
						"\"MONDAY\"",
						() -> DayOfWeek.MONDAY
				),
				test(
						DayOfWeekTypeAdapter.getDefaultInstance(),
						"\"FRIDAY\"",
						() -> DayOfWeek.FRIDAY
				)
		);
	}

}
