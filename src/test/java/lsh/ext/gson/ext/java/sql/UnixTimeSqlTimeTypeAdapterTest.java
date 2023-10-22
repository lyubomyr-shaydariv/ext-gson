package lsh.ext.gson.ext.java.sql;

import java.sql.Time;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.ext.java.util.UnixTimeDateTypeAdapterFactory;
import org.junit.jupiter.params.provider.Arguments;

public final class UnixTimeSqlTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<Time, Time> {

	@Nullable
	@Override
	protected Time normalize(@Nullable final Time value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						UnixTimeDateTypeAdapterFactory.Adapter.getInstance(() -> new Time(0)),
						"1488929283",
						new Time(1488929283000L)
				)
		);
	}

}
