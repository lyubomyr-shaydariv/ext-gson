package lsh.ext.gson.adapters;

import java.io.StringReader;
import java.util.Iterator;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.Test;

import static java.util.Objects.hash;

import static lsh.ext.gson.adapters.JsonReaderIterator.getJsonReaderIterator;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public final class JsonReaderIteratorTest {

	@Test
	public void testSome() {
		final JsonReader in = new JsonReader(new StringReader("[{\"foo\":1,\"bar\":2},{\"foo\":3,\"bar\":4},{\"foo\":5,\"bar\":6}]"));
		final Iterator<Object> unit = getJsonReaderIterator(FooBar.class, new Gson(), in);
		assertThat(unit.hasNext(), is(true));
		assertThat(unit.next(), is(new FooBar(1, 2)));
		assertThat(unit.hasNext(), is(true));
		assertThat(unit.next(), is(new FooBar(3, 4)));
		assertThat(unit.hasNext(), is(true));
		assertThat(unit.next(), is(new FooBar(5, 6)));
		assertThat(unit.hasNext(), is(false));
	}

	@Test
	public void testEmpty() {
		final JsonReader in = new JsonReader(new StringReader("[]"));
		final Iterator<Object> unit = getJsonReaderIterator(FooBar.class, new Gson(), in);
		assertThat(unit.hasNext(), is(false));
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
			return hash(foo, bar);
		}

	}

}
