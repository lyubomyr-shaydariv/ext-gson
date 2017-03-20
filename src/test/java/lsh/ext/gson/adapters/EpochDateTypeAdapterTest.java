package lsh.ext.gson.adapters;

import java.io.IOException;
import java.util.Date;

import com.google.gson.TypeAdapter;
import org.junit.Test;

import static lsh.ext.gson.adapters.EpochDateTypeAdapter.getEpochDateTypeAdapter;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public final class EpochDateTypeAdapterTest {

	@Test
	public void testRead()
			throws IOException {
		final TypeAdapter<Date> unit = getEpochDateTypeAdapter();
		assertThat(unit.fromJson("0"), is(new Date(0)));
		assertThat(unit.fromJson("1488929283"), is(new Date(1488929283)));
		assertThat(unit.fromJson("null"), nullValue());
	}

	@Test
	public void testWrite() {
		final TypeAdapter<Date> unit = getEpochDateTypeAdapter();
		assertThat(unit.toJson(new Date(0)), is("0"));
		assertThat(unit.toJson(new Date(1488929283)), is("1488929283"));
		assertThat(unit.toJson(null), is("null"));
	}

}
