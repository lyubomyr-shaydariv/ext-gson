package lsh.ext.gson.adapters.java8.time;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class OffsetDateTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<OffsetDateTime> {

	public OffsetDateTimeTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final OffsetDateTime value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						OffsetDateTimeTypeAdapter.get(),
						"\"1996-10-18T12:34:45Z\"",
						() -> OffsetDateTime.of(1996, 10, 18, 12, 34, 45, 0, ZoneOffset.UTC)
				),
				test(
						OffsetDateTimeTypeAdapter.get(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
						"\"1996-10-18T12:34:45Z\"",
						() -> OffsetDateTime.of(1996, 10, 18, 12, 34, 45, 0, ZoneOffset.UTC)
				)
		);
	}

}
