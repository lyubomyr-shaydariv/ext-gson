package lsh.ext.gson.adapters.guava;

import java.lang.reflect.Type;

import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class MultisetTypeAdapterFactoryTest {

	private static final Gson gson = new GsonBuilder()
			.registerTypeAdapterFactory(MultisetTypeAdapterFactory.get())
			.create();

	private static final Type stringToStringMultisetType = new TypeToken<Multiset<String>>() {
	}.getType();

	private static final Multiset<String> simpleMultiset = ImmutableMultiset.of("foo", "foo", "bar", "bar", "baz");
	private static final String SIMPLE_MULTISET_JSON = "[\"foo\",\"foo\",\"bar\",\"bar\",\"baz\"]";

	@Test
	public void testWrite() {
		final String json = gson.toJson(simpleMultiset, stringToStringMultisetType);
		MatcherAssert.assertThat(json, CoreMatchers.is(SIMPLE_MULTISET_JSON));
	}

	@Test
	public void testRead() {
		final Multiset<String> multiset = gson.fromJson(SIMPLE_MULTISET_JSON, stringToStringMultisetType);
		MatcherAssert.assertThat(multiset, CoreMatchers.is(simpleMultiset));
	}

}
