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
	protected OffsetDateTime normalize(@Nullable final OffsetDateTime value) {
		return value;
	}

	@Override
	protected Stream<Arguments> makeTestCases() {
		return Stream.of(
				makeTestCase(
						OffsetDateTimeTypeAdapter.getInstance(),
						"\"1996-10-18T12:34:45Z\"",
						OffsetDateTime.of(1996, 10, 18, 12, 34, 45, 0, ZoneOffset.UTC)
				),
				makeTestCase(
						OffsetDateTimeTypeAdapter.getInstance(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
						"\"1996-10-18T12:34:45Z\"",
						OffsetDateTime.of(1996, 10, 18, 12, 34, 45, 0, ZoneOffset.UTC)
				)
		);
	}

}
