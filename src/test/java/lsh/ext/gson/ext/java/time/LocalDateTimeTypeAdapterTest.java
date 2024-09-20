package lsh.ext.gson.ext.java.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class LocalDateTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<LocalDateTime, LocalDateTime> {

	@Nullable
	@Override
	protected LocalDateTime normalize(@Nullable final LocalDateTime value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(Java8TimeTypeAdapter.getLocalDateTimeTypeAdapter(), "\"2018-01-01T15:31:00\"", LocalDateTime.of(2018, 1, 1, 15, 31)),
				makeTestCase(Java8TimeTypeAdapter.getLocalDateTimeTypeAdapter(DateTimeFormatter.ISO_LOCAL_DATE_TIME), "\"2018-01-01T15:31:00\"", LocalDateTime.of(2018, 1, 1, 15, 31))
		);
	}

}
