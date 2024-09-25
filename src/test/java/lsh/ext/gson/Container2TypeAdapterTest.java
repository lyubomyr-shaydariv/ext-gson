package lsh.ext.gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.TypeAdapter;
import lsh.ext.gson.test.TestTypeAdapters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class Container2TypeAdapterTest {

	@Test
	public void testWriteReadRoundTrip()
			throws IOException {
		final TypeAdapter<Map<Integer, String>> unit = Container2TypeAdapter.getInstance(
				TestTypeAdapters.stringTypeAdapter,
				m -> m.entrySet().iterator(),
				Map.Entry::getKey,
				Map.Entry::getValue,
				String::valueOf,
				Integer::valueOf,
				IBuilder2.<Integer, String, Map<Integer, String>>fromMap(HashMap::new)
		);
		final Map<Integer, String> before = ImmutableMap.of(1, "foo", 2, "bar", 3, "baz"); // Google Guava immutable map preserves order
		final String json = unit.toJson(before);
		Assertions.assertEquals("{\"1\":\"foo\",\"2\":\"bar\",\"3\":\"baz\"}", json);
		final Map<Integer, String> after = unit.fromJson(json);
		Assertions.assertEquals(before, after);
	}

}
