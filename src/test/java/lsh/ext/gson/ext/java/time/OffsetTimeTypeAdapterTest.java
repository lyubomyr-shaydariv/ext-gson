package lsh.ext.gson.ext.java.time;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class OffsetTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<OffsetTime, OffsetTime> {

	@Nullable
	@Override
	protected OffsetTime finalize(@Nullable final OffsetTime value) {
		return value;
	}

	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						OffsetTimeTypeAdapter.getInstance(),
						"\"12:34:56Z\"",
						() -> OffsetTime.of(12, 34, 56, 0, ZoneOffset.UTC)
				),
				test(
						OffsetTimeTypeAdapter.create(DateTimeFormatter.ISO_OFFSET_TIME),
						"\"12:34:56Z\"",
						() -> OffsetTime.of(12, 34, 56, 0, ZoneOffset.UTC)
				)
		);
	}

}
