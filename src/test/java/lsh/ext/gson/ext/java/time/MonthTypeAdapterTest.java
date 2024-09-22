package lsh.ext.gson.ext.java.time;

import java.time.Month;
import java.util.List;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class MonthTypeAdapterTest
		extends AbstractTypeAdapterTest<Month, Month> {

	private static final TypeAdapter<Month> unit = Java8TimeTypeAdapter.defaultForMonth;

	@Nullable
	@Override
	protected Month normalize(@Nullable final Month value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(unit, "\"SEPTEMBER\"", Month.SEPTEMBER)
		);
	}

}
