package lsh.ext.gson.adapters.guava;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class MultisetTypeAdapterTest {

	private static final Gson gson = new Gson();

	private static final TypeAdapter<String> stringTypeAdapter = gson.getAdapter(String.class);

	private static final Multiset<String> simpleMultiset = ImmutableMultiset.of("foo", "foo", "bar", "bar", "baz");
	private static final String SIMPLE_MULTISET_JSON = "[\"foo\",\"foo\",\"bar\",\"bar\",\"baz\"]";

	@Test
	public void testWrite()
			throws IOException {
		final TypeAdapter<Multiset<String>> unit = MultisetTypeAdapter.get(stringTypeAdapter);
		final Writer writer = new StringWriter();
		final JsonWriter jsonWriter = new JsonWriter(writer);
		unit.write(jsonWriter, simpleMultiset);
		MatcherAssert.assertThat(writer.toString(), CoreMatchers.is(SIMPLE_MULTISET_JSON));
	}

	@Test
	public void testRead()
			throws IOException {
		final TypeAdapter<Multiset<String>> unit = MultisetTypeAdapter.get(stringTypeAdapter);
		final Reader reader = new StringReader(SIMPLE_MULTISET_JSON);
		final JsonReader jsonReader = new JsonReader(reader);
		final Multiset<String> multiset = unit.read(jsonReader);
		MatcherAssert.assertThat(multiset, CoreMatchers.is(simpleMultiset));
	}

}
