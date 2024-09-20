package lsh.ext.gson.ext.java.util.stream;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import lsh.ext.gson.AbstractElementCursorTypeAdapterTest;
import lsh.ext.gson.test.TypeAdapters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.mockito.Mockito;

public final class StreamTypeAdapterTest
		extends AbstractElementCursorTypeAdapterTest<Stream<?>> {

	@Override
	@Nullable
	protected List<?> normalize(@Nullable final Stream<?> value) {
		return value != null ? value.toList() : null;
	}

	@Override
	protected List<Arguments> makeTestCases() {
		return List.of(
				makeTestCase(
						StreamTypeAdapter.getInstance(TypeAdapters.integerTypeAdapter),
						"[1,2,4,8]",
						Stream.of(1, 2, 4, 8)
				)
		);
	}

	@Test
	public void testLaziness()
			throws IOException {
		final TypeAdapter<Stream<? extends Integer>> unit = StreamTypeAdapter.getInstance(TypeAdapters.primitiveIntTypeAdapter);
		final JsonReader in = new JsonReader(new StringReader("[1,2,4,8]"));
		final Stream<? extends Integer> stream = unit.read(in);
		final Iterator<? extends Integer> iterator = stream.iterator();
		Assertions.assertEquals("$", in.getPath());
		in.beginArray();
		Assertions.assertEquals(1, iterator.next());
		Assertions.assertEquals("$[1]", in.getPath());
		Assertions.assertEquals(2, iterator.next());
		Assertions.assertEquals("$[2]", in.getPath());
		Assertions.assertEquals(4, iterator.next());
		Assertions.assertEquals("$[3]", in.getPath());
		Assertions.assertEquals(8, iterator.next());
		Assertions.assertEquals("$[4]", in.getPath());
		in.endArray();
		Assertions.assertEquals("$", in.getPath());
		Assertions.assertEquals(JsonToken.END_DOCUMENT, in.peek());
		final IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class, in::nextNull);
		Assertions.assertTrue(ex.getMessage().startsWith("Expected null but was END_DOCUMENT at line 1 column 10 path $"));
	}

	@Test
	public void testLazinessClosingTheStream()
			throws IOException {
		final TypeAdapter<Stream<? extends Integer>> unit = StreamTypeAdapter.getInstance(TypeAdapters.primitiveIntTypeAdapter);
		final JsonReader inSpy = Mockito.spy(new JsonReader(new StringReader("[1,2,4,8]")));
		final Stream<? extends Integer> stream = unit.read(inSpy);
		final Iterator<? extends Integer> iterator = stream.iterator();
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
		Mockito.verify(inSpy, Mockito.never())
				.close();
		stream.close();
		Mockito.verify(inSpy)
				.close();
		final IllegalStateException ex = Assertions.assertThrows(IllegalStateException.class, inSpy::nextNull);
		Assertions.assertEquals("JsonReader is closed", ex.getMessage());
	}

	@Test
	public void testLazinessClosingTheStreamFails()
			throws IOException {
		final TypeAdapter<Stream<? extends Integer>> unit = StreamTypeAdapter.getInstance(TypeAdapters.primitiveIntTypeAdapter);
		final IOException expectedEx = new IOException();
		final JsonReader inSpy = Mockito.spy(new JsonReader(new StringReader("[1,2,4,8]")) {
			@Override
			public void close()
					throws IOException {
				throw expectedEx;
			}
		});
		final Stream<? extends Integer> stream = unit.read(inSpy);
		final RuntimeException ex = Assertions.assertThrows(RuntimeException.class, stream::close);
		Assertions.assertSame(expectedEx, ex.getCause());
	}

}
