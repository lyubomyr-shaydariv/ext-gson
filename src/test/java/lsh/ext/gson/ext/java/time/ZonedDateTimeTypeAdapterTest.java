package lsh.ext.gson.ext.java.time;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class ZonedDateTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<ZonedDateTime, ZonedDateTime> {

	@Nullable
	@Override
	protected ZonedDateTime finalize(@Nullable final ZonedDateTime value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						ZonedDateTimeTypeAdapter.getDefaultInstance(),
						"\"2018-03-20T12:34:56.000000666Z[UTC]\"",
						() -> ZonedDateTime.of(2018, 03, 20, 12, 34, 56, 666, ZoneId.of("UTC"))
				)
		);
	}

}
