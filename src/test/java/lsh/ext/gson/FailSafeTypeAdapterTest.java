package lsh.ext.gson;

import java.io.IOException;
import java.util.List;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lsh.ext.gson.test.TestTypeAdapters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class FailSafeTypeAdapterTest {

	@Test
	public void testReadSucceeds()
			throws IOException {
		final TypeAdapter<List<String>> unit = FailSafeTypeAdapter.getInstance(TestTypeAdapters.stringListTypeAdapter, true, () -> {
			throw new AssertionError();
		});
		final Iterable<String> actual = unit.fromJson("[\"foo\",\"bar\"]");
		Assertions.assertIterableEquals(List.of("foo", "bar"), actual);
	}

	@Test
	public void testReadFails()
			throws IOException {
		final TypeAdapter<String> unit = FailSafeTypeAdapter.getInstance(getBadTypeAdapter(), true, () -> "error");
		final String actual = unit.fromJson("\"foo\"");
		Assertions.assertEquals("error", actual);
	}

	private static TypeAdapter<String> getBadTypeAdapter() {
		return new TypeAdapter<>() {
			@Override
			public void write(final JsonWriter out, final String value) {
				throw new AssertionError();
			}

			@Override
			public String read(final JsonReader in)
					throws IOException {
				throw new RuntimeException(in.nextName());
			}
		};
	}

}
