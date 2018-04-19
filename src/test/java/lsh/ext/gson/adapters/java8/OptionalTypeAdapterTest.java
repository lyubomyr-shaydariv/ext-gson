package lsh.ext.gson.adapters.java8;

import java.io.IOException;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class OptionalTypeAdapterTest {

	private static final Gson gson = new Gson();

	private static final TypeAdapter<String> stringTypeAdapter = gson.getAdapter(String.class);

	@Test
	public void testRead()
			throws IOException {
		final TypeAdapter<Optional<String>> unit = OptionalTypeAdapter.get(stringTypeAdapter);
		MatcherAssert.assertThat(unit.fromJson("null"), CoreMatchers.is(Optional.empty()));
		MatcherAssert.assertThat(unit.fromJson("\"foo\""), CoreMatchers.is(Optional.of("foo")));
	}

	@Test
	public void testWrite() {
		final TypeAdapter<Optional<String>> unit = OptionalTypeAdapter.get(stringTypeAdapter);
		MatcherAssert.assertThat(unit.toJson(Optional.empty()), CoreMatchers.is("null"));
		MatcherAssert.assertThat(unit.toJson(Optional.of("foo")), CoreMatchers.is("\"foo\""));
	}

}