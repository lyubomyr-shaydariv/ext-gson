package lsh.ext.gson.adapters;

import java.io.StringReader;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import lsh.ext.gson.GsonBuilders;
import lsh.ext.gson.ext.java.ICloseableIterator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class JsonReaderIteratorTest {

	@Test
	public void testSome() {
		final JsonReader in = new JsonReader(new StringReader("[{\"foo\":1,\"bar\":2},{\"foo\":3,\"bar\":4},{\"foo\":5,\"bar\":6}]"));
		final Gson gson = GsonBuilders.createNormalized()
				.create();
		@SuppressWarnings("resource")
		final Iterator<?> unit = JsonReaderIterator.getInstance(gson.getAdapter(FooBar.class), in);
		Assertions.assertTrue(unit.hasNext());
		Assertions.assertEquals(new FooBar(1, 2), unit.next());
		Assertions.assertTrue(unit.hasNext());
		Assertions.assertEquals(new FooBar(3, 4), unit.next());
		Assertions.assertTrue(unit.hasNext());
		Assertions.assertEquals(new FooBar(5, 6), unit.next());
		Assertions.assertFalse(unit.hasNext());
	}

	@Test
	public void testEmpty()
			throws Exception {
		final JsonReader in = new JsonReader(new StringReader("[]"));
		final Gson gson = GsonBuilders.createNormalized()
				.create();
		try ( final ICloseableIterator<?> unit = JsonReaderIterator.getInstance(gson.getAdapter(FooBar.class), in) ) {
			Assertions.assertFalse(unit.hasNext());
		}
	}

	private record FooBar(
			int foo,
			int bar
	) {
	}

}
