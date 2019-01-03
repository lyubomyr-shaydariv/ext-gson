package lsh.ext.gson.adapters.java8.time;

import java.time.Month;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MonthTypeAdapterTest
		extends AbstractTypeAdapterTest<Month> {

	public MonthTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Month value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						MonthTypeAdapter.get(),
						"\"SEPTEMBER\"",
						() -> Month.SEPTEMBER
				)
		);
	}

}
