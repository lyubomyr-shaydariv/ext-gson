package lsh.ext.gson.ext.java.time;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class LocalTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<LocalTime, LocalTime> {

	@Nullable
	@Override
	protected LocalTime normalize(@Nullable final LocalTime value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						LocalTimeTypeAdapter.getInstance(),
						"\"10:10\"",
						LocalTime.of(10, 10, 0)
				),
				makeTestCase(
						LocalTimeTypeAdapter.getInstance(DateTimeFormatter.ISO_TIME),
						"\"19:23:33\"",
						LocalTime.of(19, 23, 33)
				)
		);
	}

}
