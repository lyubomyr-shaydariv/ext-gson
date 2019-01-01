package lsh.ext.gson.adapters.java8.time;

import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class OffsetTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<OffsetTime> {

	public OffsetTimeTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final OffsetTime value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				Arguments.of(OffsetTimeTypeAdapter.get(), "\"12:34:56Z\"", (Supplier<?>) () -> OffsetTime.of(12, 34, 56, 0, ZoneOffset.UTC)),
				Arguments.of(OffsetTimeTypeAdapter.get(DateTimeFormatter.ISO_OFFSET_TIME), "\"12:34:56Z\"", (Supplier<?>) () -> OffsetTime.of(12, 34, 56, 0, ZoneOffset.UTC))
		);
	}

}
