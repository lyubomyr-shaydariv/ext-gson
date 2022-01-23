package lsh.ext.gson.ext.java.time;

import java.time.Duration;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class DurationTypeAdapterTest
		extends AbstractTypeAdapterTest<Duration, Duration> {

	@Nullable
	@Override
	protected Duration finalize(@Nullable final Duration value) {
		return value;
	}

	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						DurationTypeAdapter.getInstance(),
						"\"PT0.555S\"",
						() -> Duration.ofMillis(555)
				),
				test(
						DurationTypeAdapter.getInstance(),
						"\"PT9M15S\"",
						() -> Duration.ofSeconds(555)
				),
				test(
						DurationTypeAdapter.getInstance(),
						"\"PT9H15M\"",
						() -> Duration.ofMinutes(555)
				),
				test(
						DurationTypeAdapter.getInstance(),
						"\"PT555H\"",
						() -> Duration.ofHours(555)
				),
				test(
						DurationTypeAdapter.getInstance(),
						"\"PT13320H\"",
						() -> Duration.ofDays(555)
				)
		);
	}

}
