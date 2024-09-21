package lsh.ext.gson.domain.unix;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import javax.annotation.Nullable;

import lsh.ext.gson.AbstractTypeAdapterTest;
import org.junit.jupiter.params.provider.Arguments;

@SuppressWarnings("UseOfObsoleteDateTimeApi")
public final class UnixTimeTypeAdapterTest
		extends AbstractTypeAdapterTest<java.util.Date, java.util.Date> {

	@Nullable
	@Override
	protected java.util.Date normalize(@Nullable final java.util.Date value) {
		return value;
	}

	@Override
	@SuppressWarnings("UnnecessaryFullyQualifiedName")
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						UnixTimeTypeAdapter.getInstance(java.util.Date::getTime, java.util.Date::new),
						"1488929283",
						new java.util.Date(1488929283000L)
				),
				makeTestCase(
						UnixTimeTypeAdapter.getInstance(java.sql.Date::getTime, java.sql.Date::new),
						"1488929283",
						new java.sql.Date(1488929283000L)
				),
				makeTestCase(
						UnixTimeTypeAdapter.getInstance(java.sql.Time::getTime, java.sql.Time::new),
						"1488929283",
						new Time(1488929283000L)
				),
				makeTestCase(
						UnixTimeTypeAdapter.getInstance(java.sql.Timestamp::getTime, java.sql.Timestamp::new),
						"1488929283",
						new Timestamp(1488929283000L)
				)
		);
	}

}
