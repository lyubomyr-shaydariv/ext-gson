package lsh.ext.gson.ext.java.time;

import java.time.Instant;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class InstantTypeAdapterTest
		extends AbstractTypeAdapterTest<Instant, Instant> {

	@Nullable
	@Override
	protected Instant finalize(@Nullable final Instant value) {
		return value;
	}

	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						InstantTypeAdapter.getDefaultInstance(),
						"\"1970-01-01T00:00:00Z\"",
						() -> Instant.ofEpochMilli(0)
				),
				test(
						InstantTypeAdapter.getDefaultInstance(),
						"\"2061-08-14T09:37:12.837Z\"",
						() -> Instant.ofEpochMilli(2891237832837L)
				)
		);
	}

}
