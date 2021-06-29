package lsh.ext.gson.ext.java.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class LocalDateTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<LocalDateTime, LocalDateTime> {

	@Nullable
	@Override
	protected LocalDateTime finalize(@Nullable final LocalDateTime value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						LocalDateTimeTypeAdapter.getDefaultInstance(),
						"\"2018-01-01T15:31\"",
						() -> LocalDateTime.of(2018, 01, 01, 15, 31)
				),
				test(
						LocalDateTimeTypeAdapter.create(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
						"\"2018-01-01T15:31:00\"",
						() -> LocalDateTime.of(2018, 01, 01, 15, 31)
				)
		);
	}

}
