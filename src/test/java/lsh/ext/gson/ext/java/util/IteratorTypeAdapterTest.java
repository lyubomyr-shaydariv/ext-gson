package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import lsh.ext.gson.AbstractElementCursorTypeAdapterTest;
import lsh.ext.gson.Gsons;
import lsh.ext.gson.test.TypeAdapters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.Mockito;

public final class IteratorTypeAdapterTest
		extends AbstractElementCursorTypeAdapterTest<Iterator<?>> {

	private static final Gson gson = Gsons.getNormalized();

	@Override
	@Nullable
	protected List<?> normalize(@Nullable final Iterator<?> value) {
		return value != null ? StreamSupport.stream(Spliterators.spliteratorUnknownSize(value, Spliterator.IMMUTABLE), false).toList() : null;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						IteratorTypeAdapter.getInstance(gson.getAdapter(Integer.class)),
						"[1,2,4,8]",
						List.of(1, 2, 4, 8).iterator()
				)
		);
	}

	@Test
	public void testLaziness()
			throws IOException {
		final TypeAdapter<Iterator<? extends Integer>> unit = IteratorTypeAdapter.getInstance(TypeAdapters.intTypeAdapter);
		final JsonReader inSpy = Mockito.spy(new JsonReader(new StringReader("[1,2,4,8]")));
		final Iterator<? extends Integer> iterator = unit.read(inSpy);
		Assertions.assertEquals("$", inSpy.getPath());
		inSpy.beginArray();
		Assertions.assertEquals(1, iterator.next());
		Assertions.assertEquals("$[1]", inSpy.getPath());
		Assertions.assertEquals(2, iterator.next());
		Assertions.assertEquals("$[2]", inSpy.getPath());
		Assertions.assertEquals(4, iterator.next());
		Assertions.assertEquals("$[3]", inSpy.getPath());
		Assertions.assertEquals(8, iterator.next());
		Assertions.assertEquals("$[4]", inSpy.getPath());
		inSpy.endArray();
		Assertions.assertEquals("$", inSpy.getPath());
		Mockito.verify(inSpy, Mockito.never())
				.close();
	}

	@Test
	public void testLazinessClosingTheInputFails()
			throws IOException {
		final TypeAdapter<Iterator<? extends Integer>> unit = IteratorTypeAdapter.getInstance(TypeAdapters.intTypeAdapter);
		final IOException expectedEx = new IOException();
		final JsonReader inSpy = Mockito.spy(new JsonReader(new StringReader("[1,2,4,8]")) {
			@Override
			public void close()
					throws IOException {
				throw expectedEx;
			}
		});
		final Iterator<? extends Integer> iterator = unit.read(inSpy);
		Assertions.assertEquals("$", inSpy.getPath());
		inSpy.beginArray();
		Assertions.assertEquals(1, iterator.next());
		Assertions.assertEquals("$[1]", inSpy.getPath());
		final IOException ex = Assertions.assertThrows(IOException.class, inSpy::close);
		Assertions.assertSame(expectedEx, ex);
	}

}
