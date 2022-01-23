package lsh.ext.gson.ext.java.time;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class YearTypeAdapterTest
		extends AbstractTypeAdapterTest<Year, Year> {

	@Nullable
	@Override
	protected Year finalize(@Nullable final Year value) {
		return value;
	}

	@Override
	protected Stream<Arguments> source() {
		return Stream.of(
				test(
						YearTypeAdapter.getDefaultInstance(),
						"\"2018\"",
						() -> Year.of(2018)
				),
				test(
						YearTypeAdapter.create(DateTimeFormatter.ofPattern("y'-xx-xx'")),
						"\"2018-xx-xx\"",
						() -> Year.of(2018)
				)
		);
	}

}
