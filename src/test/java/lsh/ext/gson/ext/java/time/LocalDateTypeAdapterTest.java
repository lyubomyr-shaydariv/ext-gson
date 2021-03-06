package lsh.ext.gson.ext.java.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class LocalDateTypeAdapterTest
		extends AbstractTypeAdapterTest<LocalDate, LocalDate> {

	@Nullable
	@Override
	protected LocalDate finalize(@Nullable final LocalDate value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						LocalDateTypeAdapter.getDefaultInstance(),
						"\"2018-01-01\"",
						() -> LocalDate.of(2018, 01, 01)
				),
				test(
						LocalDateTypeAdapter.create(DateTimeFormatter.ISO_LOCAL_DATE),
						"\"2018-01-01\"",
						() -> LocalDate.of(2018, 01, 01)
				)
		);
	}

}
