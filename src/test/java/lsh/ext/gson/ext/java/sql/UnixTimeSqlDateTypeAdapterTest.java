package lsh.ext.gson.ext.java.sql;

import java.sql.Date;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.ext.java.util.UnixTimeDateTypeAdapter;
import org.junit.jupiter.params.provider.Arguments;

public final class UnixTimeSqlDateTypeAdapterTest
		extends AbstractTypeAdapterTest<Date, Date> {

	@Nullable
	@Override
	protected Date normalize(@Nullable final Date value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						UnixTimeDateTypeAdapter.getInstance(() -> new Date(0)),
						"1488929283",
						new Date(1488929283000L)
				)
		);
	}

}
