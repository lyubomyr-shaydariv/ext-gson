package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.Date;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.MoreMatchers;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class EpochDateTypeAdapterTest {

	@Test
	public void testRead()
			throws IOException {
		final TypeAdapter<Date> unit = EpochDateTypeAdapter.get();
		final Date epochStart = unit.fromJson("0");
		MatcherAssert.assertThat(epochStart, CoreMatchers.is(new Date(0)));
		MatcherAssert.assertThat(epochStart, MoreMatchers.isDateTime(1970, 1, 1, 0, 0, 0));
		final Date date = unit.fromJson("1488929283");
		MatcherAssert.assertThat(date, CoreMatchers.is(new Date(1488929283000L)));
		MatcherAssert.assertThat(date, MoreMatchers.isDateTime(2017, 3, 7, 23, 28, 3));
		final Date noDate = unit.fromJson("null");
		MatcherAssert.assertThat(noDate, CoreMatchers.nullValue());
	}

	@Test
	public void testWrite() {
		final TypeAdapter<Date> unit = EpochDateTypeAdapter.get();
		MatcherAssert.assertThat(unit.toJson(new Date(0)), CoreMatchers.is("0"));
		MatcherAssert.assertThat(unit.toJson(new Date(1488929283000L)), CoreMatchers.is("1488929283"));
		MatcherAssert.assertThat(unit.toJson(null), CoreMatchers.is("null"));
	}

}
