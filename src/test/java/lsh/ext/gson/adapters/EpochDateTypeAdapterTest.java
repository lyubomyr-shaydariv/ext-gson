package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.Date;

import com.google.gson.TypeAdapter;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class EpochDateTypeAdapterTest {

	@Test
	public void testRead()
			throws IOException {
		final TypeAdapter<Date> unit = EpochDateTypeAdapter.getEpochDateTypeAdapter();
		MatcherAssert.assertThat(unit.fromJson("0"), CoreMatchers.is(new Date(0)));
		MatcherAssert.assertThat(unit.fromJson("1488929283"), CoreMatchers.is(new Date(1488929283)));
		MatcherAssert.assertThat(unit.fromJson("null"), CoreMatchers.nullValue());
	}

	@Test
	public void testWrite() {
		final TypeAdapter<Date> unit = EpochDateTypeAdapter.getEpochDateTypeAdapter();
		MatcherAssert.assertThat(unit.toJson(new Date(0)), CoreMatchers.is("0"));
		MatcherAssert.assertThat(unit.toJson(new Date(1488929283)), CoreMatchers.is("1488929283"));
		MatcherAssert.assertThat(unit.toJson(null), CoreMatchers.is("null"));
	}

}
