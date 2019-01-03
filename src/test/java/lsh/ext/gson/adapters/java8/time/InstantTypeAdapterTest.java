package lsh.ext.gson.adapters.java8.time;

import java.time.Instant;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class InstantTypeAdapterTest
		extends AbstractTypeAdapterTest<Instant> {

	public InstantTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Instant value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						InstantTypeAdapter.get(),
						"\"1970-01-01T00:00:00Z\"",
						() -> Instant.ofEpochMilli(0)
				),
				test(
						InstantTypeAdapter.get(),
						"\"2061-08-14T09:37:12.837Z\"",
						() -> Instant.ofEpochMilli(2891237832837L)
				)
		);
	}

}
