package lsh.ext.gson.ext.java.time;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class YearTypeAdapterTest
		extends AbstractTypeAdapterTest<Year, Year> {

	@Nullable
	@Override
	protected Year normalize(@Nullable final Year value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						YearTypeAdapterFactory.Adapter.getInstance(),
						"\"2018\"",
						Year.of(2018)
				),
				makeTestCase(
						YearTypeAdapterFactory.Adapter.getInstance(DateTimeFormatter.ofPattern("y'-xx-xx'")),
						"\"2018-xx-xx\"",
						Year.of(2018)
				)
		);
	}

}
