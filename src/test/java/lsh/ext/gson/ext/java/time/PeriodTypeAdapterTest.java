package lsh.ext.gson.ext.java.time;

import java.time.Period;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class PeriodTypeAdapterTest
		extends AbstractTypeAdapterTest<Period, Period> {

	@Nullable
	@Override
	protected Period finalize(@Nullable final Period value) {
		return value;
	}

	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						PeriodTypeAdapter.getDefaultInstance(),
						"\"P1Y8M20D\"",
						() -> Period.of(1, 8, 20)
				)
		);
	}

}
