package lsh.ext.gson.adapters.java8.time;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class ZonedDateTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<ZonedDateTime> {

	public ZonedDateTimeTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final ZonedDateTime value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				Arguments.of(ZonedDateTimeTypeAdapter.get(), "\"2018-03-20T12:34:56.000000666Z[UTC]\"", (Supplier<?>) () -> ZonedDateTime.of(2018, 03, 20, 12, 34, 56, 666, ZoneId.of("UTC")))
		);
	}

}
