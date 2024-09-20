package lsh.ext.gson.ext.java.time;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class OffsetTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<OffsetTime, OffsetTime> {

	@Nullable
	@Override
	protected OffsetTime normalize(@Nullable final OffsetTime value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(Java8TimeTypeAdapter.getOffsetTimeTypeAdapter(), "\"12:34:56Z\"", OffsetTime.of(12, 34, 56, 0, ZoneOffset.UTC)),
				makeTestCase(Java8TimeTypeAdapter.getOffsetTimeTypeAdapter(DateTimeFormatter.ISO_OFFSET_TIME), "\"12:34:56Z\"", OffsetTime.of(12, 34, 56, 0, ZoneOffset.UTC))
		);
	}

}
