package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.Map;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.test.TypeAdapters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class MapEntryTypeAdapterTest {

	private static final TypeAdapter<Map.Entry<Double, Integer>> unit = MapEntryTypeAdapter.getInstance(TypeAdapters.intTypeAdapter, String::valueOf, Double::parseDouble);

	@Test
	public void testReadForNoProperties()
			throws IOException {
		final Map.Entry<Double, Integer> e = unit.fromJson("{}");
		Assertions.assertNull(e.getKey());
		Assertions.assertNull(e.getValue());
	}

	@Test
	public void testReadForSingleProperty()
			throws IOException {
		final Map.Entry<Double, Integer> e = unit.fromJson("{\"3.14\":1}");
		Assertions.assertEquals(3.14D, e.getKey());
		Assertions.assertEquals(1, e.getValue());
	}

	@Test
	public void testReadForMultipleProperties() {
		Assertions.assertThrows(
				IllegalStateException.class,
				() -> unit.fromJson("{\"3.14\":1,\"2.7\":3,\"UNREACHABLE\":\"UNREACHABLE\"}"),
				"Expected a single property object with key `3.14` but also encountered `2.7`"
		);
	}

}
