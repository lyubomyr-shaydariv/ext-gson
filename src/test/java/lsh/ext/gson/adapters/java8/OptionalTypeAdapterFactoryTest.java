package lsh.ext.gson.adapters.java8;

import java.io.IOException;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class OptionalTypeAdapterFactoryTest {

	private static final Gson gson = new Gson();

	private static final TypeToken<Optional<String>> optionalStringTypeToken = new TypeToken<Optional<String>>() {
	};

	@Test
	public void testRead()
			throws IOException {
		final TypeAdapterFactory unit = OptionalTypeAdapterFactory.get();
		final TypeAdapter<Optional<String>> optionalTypeAdapter = unit.create(gson, optionalStringTypeToken);
		MatcherAssert.assertThat(optionalTypeAdapter.fromJson("null"), CoreMatchers.is(Optional.empty()));
		MatcherAssert.assertThat(optionalTypeAdapter.fromJson("\"foo\""), CoreMatchers.is(Optional.of("foo")));
	}

	@Test
	public void testWrite() {
		final TypeAdapterFactory unit = OptionalTypeAdapterFactory.get();
		final TypeAdapter<Optional<String>> optionalTypeAdapter = unit.create(gson, optionalStringTypeToken);
		MatcherAssert.assertThat(optionalTypeAdapter.toJson(Optional.empty()), CoreMatchers.is("null"));
		MatcherAssert.assertThat(optionalTypeAdapter.toJson(Optional.of("foo")), CoreMatchers.is("\"foo\""));
	}

}
