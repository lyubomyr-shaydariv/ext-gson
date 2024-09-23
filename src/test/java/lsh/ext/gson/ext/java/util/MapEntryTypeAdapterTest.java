package lsh.ext.gson.ext.java.util;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;

import com.google.gson.TypeAdapter;
import lsh.ext.gson.test.MoreAssertions;
import lsh.ext.gson.test.TestTypeAdapters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class MapEntryTypeAdapterTest {

	private static final TypeAdapter<Map.Entry<Double, Integer>> unit = MapEntryTypeAdapter.getInstance(TestTypeAdapters.primitiveIntTypeAdapter, String::valueOf, Double::parseDouble, AbstractMap.SimpleImmutableEntry::new);

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
		MoreAssertions.assertThrows(IllegalStateException.class, "Expected a single property object with key `3.14` but also encountered `2.7`", () -> unit.fromJson("{\"3.14\":1,\"2.7\":3,\"UNREACHABLE\":\"UNREACHABLE\"}"));
	}

}
