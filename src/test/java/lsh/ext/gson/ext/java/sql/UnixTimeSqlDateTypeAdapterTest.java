package lsh.ext.gson.ext.java.sql;

import java.sql.Date;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.domain.UnixTimeTypeAdapter;
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
						UnixTimeTypeAdapter.getInstance(Converters.dateConverter),
						"1488929283",
						new Date(1488929283000L)
				)
		);
	}

}
