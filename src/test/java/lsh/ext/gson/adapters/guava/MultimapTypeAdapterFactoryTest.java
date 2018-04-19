package lsh.ext.gson.adapters.guava;

import java.lang.reflect.Type;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class MultimapTypeAdapterFactoryTest {

	private static final Gson gson = new GsonBuilder()
			.registerTypeAdapterFactory(MultimapTypeAdapterFactory.get())
			.create();

	private static final Type stringToStringMultimapType = new TypeToken<Multimap<String, String>>() {
	}.getType();

	private static final Multimap<String, String> simpleMultimap = ImmutableMultimap.<String, String>builder()
			.put("1", "foo")
			.put("1", "bar")
			.put("1", "baz")
			.put("2", "foo")
			.put("2", "bar")
			.put("2", "baz")
			.build();
	private static final String SIMPLE_MULTIMAP_JSON = "{\"1\":\"foo\",\"1\":\"bar\",\"1\":\"baz\",\"2\":\"foo\",\"2\":\"bar\",\"2\":\"baz\"}";

	@Test
	public void testWrite() {
		final String json = gson.toJson(simpleMultimap, stringToStringMultimapType);
		MatcherAssert.assertThat(json, CoreMatchers.is(SIMPLE_MULTIMAP_JSON));
	}

	@Test
	public void testRead() {
		final Multimap<String, String> multimap = gson.fromJson(SIMPLE_MULTIMAP_JSON, stringToStringMultimapType);
		MatcherAssert.assertThat(multimap, CoreMatchers.is(simpleMultimap));
	}

}
