package lsh.ext.gson.adapters.java8.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class LocalTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<LocalTime, LocalTime> {

	@Nullable
	@Override
	protected LocalTime finalize(@Nullable final LocalTime value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						LocalTimeTypeAdapter.get(),
						"\"10:10\"",
						() -> LocalTime.of(10, 10, 0)
				),
				test(
						LocalTimeTypeAdapter.get(DateTimeFormatter.ISO_TIME),
						"\"19:23:33\"",
						() -> LocalTime.of(19, 23, 33)
				)
		);
	}

}
