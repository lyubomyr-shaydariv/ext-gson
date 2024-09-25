package lsh.ext.gson;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.test.TestTypeAdapters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class Container1TypeAdapterTest {

	@Test
	public void testWriteReadRoundTrip()
			throws IOException {
		final TypeAdapter<Queue<String>> unit = Container1TypeAdapter.getInstance(
				TestTypeAdapters.stringTypeAdapter,
				IBuilder1.<String, Queue<String>>fromCollection(ArrayDeque::new)
		);
		final List<String> data = List.of("foo", "bar", "baz");
		final Queue<String> before = new ArrayDeque<>(data);
		final String json = unit.toJson(before);
		Assertions.assertEquals("[\"foo\",\"bar\",\"baz\"]", json);
		final Queue<String> after = unit.fromJson(json);
		Assertions.assertIterableEquals(data, after);
	}

}
