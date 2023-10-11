package lsh.ext.gson.ext.java.time;

import java.time.Duration;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class DurationTypeAdapterTest
		extends AbstractTypeAdapterTest<Duration, Duration> {

	@Nullable
	@Override
	protected Duration normalize(@Nullable final Duration value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						DurationTypeAdapterFactory.Adapter.getInstance(),
						"\"PT0.555S\"",
						Duration.ofMillis(555)
				),
				makeTestCase(
						DurationTypeAdapterFactory.Adapter.getInstance(),
						"\"PT9M15S\"",
						Duration.ofSeconds(555)
				),
				makeTestCase(
						DurationTypeAdapterFactory.Adapter.getInstance(),
						"\"PT9H15M\"",
						Duration.ofMinutes(555)
				),
				makeTestCase(
						DurationTypeAdapterFactory.Adapter.getInstance(),
						"\"PT555H\"",
						Duration.ofHours(555)
				),
				makeTestCase(
						DurationTypeAdapterFactory.Adapter.getInstance(),
						"\"PT13320H\"",
						Duration.ofDays(555)
				)
		);
	}

}
