package lsh.ext.gson.ext.java.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class LocalDateTypeAdapterTest
		extends AbstractTypeAdapterTest<LocalDate, LocalDate> {

	@Nullable
	@Override
	protected LocalDate normalize(@Nullable final LocalDate value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						LocalDateTypeAdapter.getInstance(),
						"\"2018-01-01\"",
						LocalDate.of(2018, 01, 01)
				),
				makeTestCase(
						LocalDateTypeAdapter.getInstance(DateTimeFormatter.ISO_LOCAL_DATE),
						"\"2018-01-01\"",
						LocalDate.of(2018, 01, 01)
				)
		);
	}

}
