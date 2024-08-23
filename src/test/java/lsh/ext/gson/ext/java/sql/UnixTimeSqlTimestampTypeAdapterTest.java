package lsh.ext.gson.ext.java.sql;

import java.sql.Timestamp;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import lsh.ext.gson.domain.UnixTimeTypeAdapter;
import org.junit.jupiter.params.provider.Arguments;

public final class UnixTimeSqlTimestampTypeAdapterTest
		extends AbstractTypeAdapterTest<Timestamp, Timestamp> {

	@Nullable
	@Override
	protected Timestamp normalize(@Nullable final Timestamp value) {
		return value;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						UnixTimeTypeAdapter.getInstance(Converters.timestampConverter),
						"1488929283",
						new Timestamp(1488929283000L)
				)
		);
	}

}
