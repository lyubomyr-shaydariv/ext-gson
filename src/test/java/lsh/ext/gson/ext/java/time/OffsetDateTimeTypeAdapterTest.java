package lsh.ext.gson.ext.java.time;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class OffsetDateTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<OffsetDateTime, OffsetDateTime> {

	@Nullable
	@Override
	protected OffsetDateTime finalize(@Nullable final OffsetDateTime value) {
		return value;
	}

	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						OffsetDateTimeTypeAdapter.getDefaultInstance(),
						"\"1996-10-18T12:34:45Z\"",
						() -> OffsetDateTime.of(1996, 10, 18, 12, 34, 45, 0, ZoneOffset.UTC)
				),
				test(
						OffsetDateTimeTypeAdapter.create(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
						"\"1996-10-18T12:34:45Z\"",
						() -> OffsetDateTime.of(1996, 10, 18, 12, 34, 45, 0, ZoneOffset.UTC)
				)
		);
	}

}
