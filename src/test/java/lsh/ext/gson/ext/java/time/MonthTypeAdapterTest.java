package lsh.ext.gson.ext.java.time;

import java.time.Month;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MonthTypeAdapterTest
		extends AbstractTypeAdapterTest<Month, Month> {

	@Nullable
	@Override
	protected Month normalize(@Nullable final Month value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						MonthTypeAdapter.getInstance(),
						"\"SEPTEMBER\"",
						Month.SEPTEMBER
				)
		);
	}

}
