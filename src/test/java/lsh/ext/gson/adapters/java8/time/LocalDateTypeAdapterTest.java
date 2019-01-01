package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class LocalDateTypeAdapterTest
		extends AbstractTypeAdapterTest<LocalDate> {

	public LocalDateTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final LocalDate value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				Arguments.of(LocalDateTypeAdapter.get(), "\"2018-01-01\"", (Supplier<?>) () -> LocalDate.of(2018, 01, 01)),
				Arguments.of(LocalDateTypeAdapter.get(DateTimeFormatter.ISO_LOCAL_DATE), "\"2018-01-01\"", (Supplier<?>) () -> LocalDate.of(2018, 01, 01))
		);
	}

}
