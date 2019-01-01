package lsh.ext.gson.adapters.java8.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class LocalDateTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<LocalDateTime> {

	public LocalDateTimeTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final LocalDateTime value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				Arguments.of(LocalDateTimeTypeAdapter.get(), "\"2018-01-01T15:31\"", (Supplier<?>) () -> LocalDateTime.of(2018, 01, 01, 15, 31)),
				Arguments.of(LocalDateTimeTypeAdapter.get(DateTimeFormatter.ISO_LOCAL_DATE_TIME), "\"2018-01-01T15:31:00\"", (Supplier<?>) () -> LocalDateTime.of(2018, 01, 01, 15, 31))
		);
	}

}
