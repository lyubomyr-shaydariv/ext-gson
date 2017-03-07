package lsh.ext.gson.adapters;

import java.util.Iterator;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import org.junit.Test;

import static lsh.ext.gson.adapters.IteratorTypeAdapter.getIteratorTypeAdapter;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public final class IteratorTypeAdapterTest {

	private static final Gson gson = new Gson();

	@Test
	public void testRead()
			throws Exception {
		final TypeAdapter<Iterator<Integer>> unit = getIteratorTypeAdapter(Integer.class, gson);
		final Iterator<Integer> iterator = unit.fromJson("[1,2,4,8]");
		assertThat(iterator instanceof AutoCloseable, is(true));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(1));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(2));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(4));
		assertThat(iterator.hasNext(), is(true));
		assertThat(iterator.next(), is(8));
		assertThat(iterator.hasNext(), is(false));
	}

	@Test
	public void testWrite() {
		final TypeAdapter<Iterator<Integer>> unit = getIteratorTypeAdapter(Integer.class, gson);
		final Iterator<Integer> iterator = ImmutableList.of(1, 2, 4, 8).iterator();
		final String json = unit.toJson(iterator);
		assertThat(json, is("[1,2,4,8]"));
	}

}
