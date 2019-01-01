package lsh.ext.gson.adapters;

import java.io.StringWriter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class ReadOnlyTypeAdapterTest {

	@Test
	public void write() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			final TypeAdapter<String> readOnlyTypeAdapter = new ReadOnlyTypeAdapter<String>() {
				@Override
				public String read(final JsonReader in) {
					throw new AssertionError();
				}
			};
			readOnlyTypeAdapter.write(new JsonWriter(new StringWriter()), "foo");
		});
	}

}
