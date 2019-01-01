package lsh.ext.gson.adapters.java8.time;

import java.time.Duration;
import java.util.function.Supplier;
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
				Arguments.of(DurationTypeAdapter.get(), "\"PT0.555S\"", (Supplier<?>) () -> Duration.ofMillis(555)),
				Arguments.of(DurationTypeAdapter.get(), "\"PT9M15S\"", (Supplier<?>) () -> Duration.ofSeconds(555)),
				Arguments.of(DurationTypeAdapter.get(), "\"PT9H15M\"", (Supplier<?>) () -> Duration.ofMinutes(555)),
				Arguments.of(DurationTypeAdapter.get(), "\"PT555H\"", (Supplier<?>) () -> Duration.ofHours(555)),
				Arguments.of(DurationTypeAdapter.get(), "\"PT13320H\"", (Supplier<?>) () -> Duration.ofDays(555))
		);
	}

}
