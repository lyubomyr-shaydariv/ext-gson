package lsh.ext.gson.adapters.java8.time;

import java.time.Duration;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class DurationTypeAdapterTest
		extends AbstractTypeAdapterTest<Duration> {

	public DurationTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Duration value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						DurationTypeAdapter.get(),
						"\"PT0.555S\"",
						() -> Duration.ofMillis(555)
				),
				test(
						DurationTypeAdapter.get(),
						"\"PT9M15S\"",
						() -> Duration.ofSeconds(555)
				),
				test(
						DurationTypeAdapter.get(),
						"\"PT9H15M\"",
						() -> Duration.ofMinutes(555)
				),
				test(
						DurationTypeAdapter.get(),
						"\"PT555H\"",
						() -> Duration.ofHours(555)
				),
				test(
						DurationTypeAdapter.get(),
						"\"PT13320H\"",
						() -> Duration.ofDays(555)
				)
		);
	}

}
