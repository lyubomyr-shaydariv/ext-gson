package lsh.ext.gson.adapters;

import java.util.Iterator;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

public final class IteratorTypeAdapterTest {

	private static final Gson gson = new Gson();

	@Test
	public void testRead()
			throws Exception {
		final TypeAdapter<Iterator<Integer>> unit = IteratorTypeAdapter.get(Integer.class, gson);
		final Iterator<Integer> iterator = unit.fromJson("[1,2,4,8]");
		MatcherAssert.assertThat(iterator instanceof AutoCloseable, CoreMatchers.is(true));
		MatcherAssert.assertThat(iterator.hasNext(), CoreMatchers.is(true));
		MatcherAssert.assertThat(iterator.next(), CoreMatchers.is(1));
		MatcherAssert.assertThat(iterator.hasNext(), CoreMatchers.is(true));
		MatcherAssert.assertThat(iterator.next(), CoreMatchers.is(2));
		MatcherAssert.assertThat(iterator.hasNext(), CoreMatchers.is(true));
		MatcherAssert.assertThat(iterator.next(), CoreMatchers.is(4));
		MatcherAssert.assertThat(iterator.hasNext(), CoreMatchers.is(true));
		MatcherAssert.assertThat(iterator.next(), CoreMatchers.is(8));
		MatcherAssert.assertThat(iterator.hasNext(), CoreMatchers.is(false));
	}

	@Test
	public void testWrite() {
		final TypeAdapter<Iterator<Integer>> unit = IteratorTypeAdapter.get(Integer.class, gson);
		final Iterator<Integer> iterator = ImmutableList.of(1, 2, 4, 8).iterator();
		final String json = unit.toJson(iterator);
		MatcherAssert.assertThat(json, CoreMatchers.is("[1,2,4,8]"));
	}

}
