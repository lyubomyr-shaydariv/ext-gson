package lsh.ext.gson.ext.java.time;

import java.time.Period;
import java.util.List;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class PeriodTypeAdapterTest
		extends AbstractTypeAdapterTest<Period, Period> {

	private static final TypeAdapter<Period> unit = Java8TimeTypeAdapter.defaultForPeriod;

	@Nullable
	@Override
	protected Period normalize(@Nullable final Period value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(unit, "\"P1Y8M20D\"", Period.of(1, 8, 20))
		);
	}

}
