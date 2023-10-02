package lsh.ext.gson.ext.java.util;

import java.util.Date;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import lsh.ext.gson.adapters.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

public final class UnixTimeDateTypeAdapterTest
		extends AbstractTypeAdapterTest<Date, Date> {

	@Nullable
	@Override
	protected Date normalize(@Nullable final Date value) {
		return value;
	}

	@Override
	protected Stream<Arguments> makeTestCases() {
		return Stream.of(
				makeTestCase(
						UnixTimeDateTypeAdapter.getInstance(),
						"1488929283",
						new Date(1488929283000L)
				)
		);
	}

}
