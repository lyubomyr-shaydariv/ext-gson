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
						UnixTimeTypeAdapter.getDefaultForJavaUtilDate(),
						"1488929283",
						new java.util.Date(1488929283000L)
				),
				makeTestCase(
						UnixTimeTypeAdapter.getDefaultForJavaSqlDate(),
						"1488929283",
						new java.sql.Date(1488929283000L)
				),
				makeTestCase(
						UnixTimeTypeAdapter.getDefaultForJavaSqlTime(),
						"1488929283",
						new Time(1488929283000L)
				),
				makeTestCase(
						UnixTimeTypeAdapter.getDefaultForJavaSqlTimestamp(),
						"1488929283",
						new Timestamp(1488929283000L)
				)
		);
	}

}
