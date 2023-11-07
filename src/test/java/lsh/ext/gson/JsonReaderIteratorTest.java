package lsh.ext.gson;

import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class JsonReaderIteratorTest {

	private static final Gson gson = Gsons.getNormalized();

	@Test
	public void testSome() {
		final JsonReader in = new JsonReader(new StringReader("[{\"foo\":1,\"bar\":2},{\"foo\":3,\"bar\":4},{\"foo\":5,\"bar\":6}]"));
		final Iterator<?> unit = JsonReaderIterator.getInstance(gson.getAdapter(FooBar.class), in);
		Assertions.assertTrue(unit.hasNext());
		Assertions.assertEquals(new FooBar(1, 2), unit.next());
		Assertions.assertTrue(unit.hasNext());
		Assertions.assertEquals(new FooBar(3, 4), unit.next());
		Assertions.assertTrue(unit.hasNext());
		Assertions.assertEquals(new FooBar(5, 6), unit.next());
		Assertions.assertFalse(unit.hasNext());
		Assertions.assertThrows(NoSuchElementException.class, unit::next);
	}

	@Test
	public void testEmpty() {
		final JsonReader in = new JsonReader(new StringReader("[]"));
		final Iterator<?> unit = JsonReaderIterator.getInstance(gson.getAdapter(FooBar.class), in);
		Assertions.assertFalse(unit.hasNext());
		Assertions.assertThrows(NoSuchElementException.class, unit::next);
	}

	private record FooBar(
			int foo,
			int bar
	) {
	}

}
