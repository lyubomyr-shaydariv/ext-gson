package lsh.ext.gson.ext.java.time;

import java.time.Instant;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class InstantTypeAdapterTest
		extends AbstractTypeAdapterTest<Instant, Instant> {

	@Nullable
	@Override
	protected Instant normalize(@Nullable final Instant value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						InstantTypeAdapterFactory.Adapter.getInstance(),
						"\"1970-01-01T00:00:00Z\"",
						Instant.ofEpochMilli(0)
				),
				makeTestCase(
						InstantTypeAdapterFactory.Adapter.getInstance(),
						"\"2061-08-14T09:37:12.837Z\"",
						Instant.ofEpochMilli(2891237832837L)
				)
		);
	}

}
