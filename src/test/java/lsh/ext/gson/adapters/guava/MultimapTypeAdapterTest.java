package lsh.ext.gson.adapters.guava;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class MultimapTypeAdapterTest {

	private static final Gson gson = new Gson();

	private static final TypeAdapter<String> stringTypeAdapter = gson.getAdapter(String.class);

	private static final Multimap<String, String> simpleMultimap = ImmutableMultimap.of("1", "foo", "1", "bar", "2", "foo", "2", "bar");
	private static final String SIMPLE_MULTIMAP_JSON = "{\"1\":\"foo\",\"1\":\"bar\",\"2\":\"foo\",\"2\":\"bar\"}";

	@Test
	public void testWrite()
			throws IOException {
		final TypeAdapter<Multimap<String, String>> unit = MultimapTypeAdapter.get(stringTypeAdapter);
		final Writer writer = new StringWriter();
		final JsonWriter jsonWriter = new JsonWriter(writer);
		unit.write(jsonWriter, simpleMultimap);
		MatcherAssert.assertThat(writer.toString(), CoreMatchers.is(SIMPLE_MULTIMAP_JSON));
	}

	@Test
	public void testRead()
			throws IOException {
		final TypeAdapter<Multimap<String, String>> unit = MultimapTypeAdapter.get(stringTypeAdapter);
		final Reader reader = new StringReader(SIMPLE_MULTIMAP_JSON);
		final JsonReader jsonReader = new JsonReader(reader);
		final Multimap<String, String> multimap = unit.read(jsonReader);
		MatcherAssert.assertThat(multimap, CoreMatchers.is(simpleMultimap));
	}

}
