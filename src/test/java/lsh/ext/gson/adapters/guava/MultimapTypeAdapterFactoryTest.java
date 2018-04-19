package lsh.ext.gson.adapters.guava;

import java.lang.reflect.Type;

import com.google.common.collect.ArrayListMultimap;
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

	private static final Type stringToObjectMultimapType = new TypeToken<Multimap<String, Object>>() {
	}.getType();

	private static final String SIMPLE_MULTIMAP_JSON = "{\"1\":\"foo\",\"1\":\"bar\",\"1\":\"baz\",\"2\":\"foo\",\"2\":\"bar\",\"2\":\"baz\"}";

	@Test
	public void testWrite() {
		final String json = gson.toJson(createMultimap(), stringToObjectMultimapType);
		MatcherAssert.assertThat(json, CoreMatchers.is(SIMPLE_MULTIMAP_JSON));
	}

	@Test
	public void testRead() {
		final Multimap<String, Object> multimap = gson.fromJson(SIMPLE_MULTIMAP_JSON, stringToObjectMultimapType);
		MatcherAssert.assertThat(multimap, CoreMatchers.is(createMultimap()));
	}

	private static Multimap<String, Object> createMultimap() {
		final Multimap<String, Object> multimap = ArrayListMultimap.create();
		multimap.put("1", "foo");
		multimap.put("1", "bar");
		multimap.put("1", "baz");
		multimap.put("2", "foo");
		multimap.put("2", "bar");
		multimap.put("2", "baz");
		return multimap;
	}

}
