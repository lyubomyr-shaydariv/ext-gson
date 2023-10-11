package lsh.ext.gson.ext.java.time;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class OffsetDateTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<OffsetDateTime, OffsetDateTime> {

	@Nullable
	@Override
	protected OffsetDateTime normalize(@Nullable final OffsetDateTime value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						OffsetDateTimeTypeAdapterFactory.Adapter.getInstance(),
						"\"1996-10-18T12:34:45Z\"",
						OffsetDateTime.of(1996, 10, 18, 12, 34, 45, 0, ZoneOffset.UTC)
				),
				makeTestCase(
						OffsetDateTimeTypeAdapterFactory.Adapter.getInstance(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
						"\"1996-10-18T12:34:45Z\"",
						OffsetDateTime.of(1996, 10, 18, 12, 34, 45, 0, ZoneOffset.UTC)
				)
		);
	}

}
