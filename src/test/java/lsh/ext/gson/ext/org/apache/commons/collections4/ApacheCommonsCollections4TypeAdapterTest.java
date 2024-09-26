package lsh.ext.gson.ext.org.apache.commons.collections4;

import java.io.IOException;
import java.util.function.Function;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.IBuilder2;
import lsh.ext.gson.test.TestTypeAdapters;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.bag.AbstractMapBag;
import org.apache.commons.collections4.bag.TreeBag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class ApacheCommonsCollections4TypeAdapterTest {

	@Test
	public void testForBagRoundTrip()
			throws IOException {
		final TypeAdapter<Bag<String>> unit = ApacheCommonsCollections4TypeAdapter.forBag(
				TestTypeAdapters.stringTypeAdapter,
				() -> Builder.forBag(TreeBag::new)
		);
		final Bag<String> before = new TreeBag<>();
		before.add("foo");
		before.add("bar");
		before.add("bar");
		before.add("baz");
		before.add("baz");
		before.add("baz");
		final String json = unit.toJson(before);
		Assertions.assertEquals("[\"bar\",\"bar\",\"baz\",\"baz\",\"baz\",\"foo\"]", json);
		final Bag<String> actual = unit.fromJson(json);
		Assertions.assertIterableEquals(before, actual);
	}

	@Test
	public void testForBagNCopiesForRoundTrip()
			throws IOException {
		@SuppressWarnings("RedundantTypeArguments")
		final TypeAdapter<Bag<String>> unit = ApacheCommonsCollections4TypeAdapter.<String>forBagNCopies(
				TestTypeAdapters.integerTypeAdapter,
				() -> IBuilder2.of(TreeBag::new, AbstractMapBag::add, Function.identity())
		);
		final Bag<String> before = new TreeBag<>();
		before.add("foo");
		before.add("bar");
		before.add("bar");
		before.add("baz");
		before.add("baz");
		before.add("baz");
		final String json = unit.toJson(before);
		Assertions.assertEquals("{\"bar\":2,\"baz\":3,\"foo\":1}", json);
		final Bag<String> actual = unit.fromJson(json);
		Assertions.assertIterableEquals(before, actual);
	}

}
