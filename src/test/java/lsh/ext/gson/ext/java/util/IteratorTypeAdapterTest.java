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
		final JsonReader in = new JsonReader(new StringReader("[1,2,4,8]"));
		final Iterator<? extends Integer> iterator = unit.read(in);
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
	}

}
