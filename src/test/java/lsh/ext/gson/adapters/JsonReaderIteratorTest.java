package lsh.ext.gson.adapters;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lsh.ext.gson.ICloseableIterator;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public final class JsonReaderIteratorTest {

	@Test
	public void testSome() {
		final JsonReader in = new JsonReader(new StringReader("[{\"foo\":1,\"bar\":2},{\"foo\":3,\"bar\":4},{\"foo\":5,\"bar\":6}]"));
		@SuppressWarnings("resource")
		final Iterator<?> unit = JsonReaderIterator.get(new Gson().getAdapter(FooBar.class), in);
		MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(true));
		MatcherAssert.assertThat(unit.next(), CoreMatchers.is(new FooBar(1, 2)));
		MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(true));
		MatcherAssert.assertThat(unit.next(), CoreMatchers.is(new FooBar(3, 4)));
		MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(true));
		MatcherAssert.assertThat(unit.next(), CoreMatchers.is(new FooBar(5, 6)));
		MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(false));
	}

	@Test
	public void testEmpty()
			throws Exception {
		final JsonReader in = new JsonReader(new StringReader("[]"));
		try ( final ICloseableIterator<?> unit = JsonReaderIterator.get(new Gson().getAdapter(FooBar.class), in) ) {
			MatcherAssert.assertThat(unit.hasNext(), CoreMatchers.is(false));
		}
	}

	private static final class FooBar {

		private final int foo;
		private final int bar;

		private FooBar(final int foo, final int bar) {
			this.foo = foo;
			this.bar = bar;
		}

		@Override
		public boolean equals(final Object o) {
			if ( this == o ) {
				return true;
			}
			if ( o == null || getClass() != o.getClass() ) {
				return false;
			}
			final FooBar fooBar = (FooBar) o;
			return Objects.equals(foo, fooBar.foo) && Objects.equals(bar, fooBar.bar);
		}

		@Override
		public int hashCode() {
			return Objects.hash(foo, bar);
		}

	}

}
