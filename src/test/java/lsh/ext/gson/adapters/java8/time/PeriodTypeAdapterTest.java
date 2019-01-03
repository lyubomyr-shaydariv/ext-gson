package lsh.ext.gson.adapters.java8.time;

import java.time.Period;
import java.util.stream.Stream;
import javax.annotation.Nonnull;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public class PeriodTypeAdapterTest
		extends AbstractTypeAdapterTest<Period> {

	public PeriodTypeAdapterTest() {
		super(null);
	}

	@Nonnull
	@Override
	protected Object finalizeValue(@Nonnull final Period value) {
		return value;
	}

	@Nonnull
	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						PeriodTypeAdapter.get(),
						"\"P1Y8M20D\"",
						() -> Period.of(1, 8, 20)
				)
		);
	}

}
