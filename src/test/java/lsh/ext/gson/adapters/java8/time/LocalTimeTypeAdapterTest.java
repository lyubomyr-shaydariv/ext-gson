package lsh.ext.gson.adapters.java8.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class LocalTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<LocalTime> {

	public LocalTimeTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final LocalTime value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				Arguments.of(LocalTimeTypeAdapter.get(), "\"10:10\"", (Supplier<?>) () -> LocalTime.of(10, 10, 0)),
				Arguments.of(LocalTimeTypeAdapter.get(DateTimeFormatter.ISO_TIME), "\"19:23:33\"", (Supplier<?>) () -> LocalTime.of(19, 23, 33))
		);
	}

}
