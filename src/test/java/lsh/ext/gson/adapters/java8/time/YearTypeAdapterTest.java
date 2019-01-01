package lsh.ext.gson.adapters.java8.time;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class YearTypeAdapterTest
		extends AbstractTypeAdapterTest<Year> {

	public YearTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Year value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				Arguments.of(YearTypeAdapter.get(), "\"2018\"", (Supplier<?>) () -> Year.of(2018)),
				Arguments.of(YearTypeAdapter.get(DateTimeFormatter.ofPattern("y'-xx-xx'")), "\"2018-xx-xx\"", (Supplier<?>) () -> Year.of(2018))
		);
	}

}
