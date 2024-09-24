package lsh.ext.gson;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class IBuilder1Test {

	@Test
	public void testFromCollector() {
		final Collector<String, ?, ? extends Map<String, Integer>> collector = ImmutableMap.toImmutableMap(Function.identity(), String::length);
		final IBuilder1<String, Map<String, Integer>> unit = IBuilder1.from(collector);
		unit.accept("Lorem");
		unit.accept("ipsum");
		unit.accept("dolor");
		unit.accept("sit");
		unit.accept("amet");
		final Map<String, Integer> actual = unit.build();
		Assertions.assertEquals(Map.of("Lorem", 5, "ipsum", 5, "dolor", 5, "sit", 3, "amet", 4), actual);
		Assertions.assertInstanceOf(ImmutableMap.class, actual);
	}

}
