package lsh.ext.gson;

import java.util.stream.Collector;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class IBuilder2Test {

	@Test
	public void testFromCollector() {
		final Collector<String, ?, ? extends Table<String, Long, Integer>> collector = ImmutableTable.toImmutableTable(
				s -> String.format("[%s]", s),
				s -> Long.valueOf(s.length() * 1000),
				String::length
		);
		final IBuilder2<Integer, Long, ? extends Table<String, Long, Integer>> unit = IBuilder2.of(collector, (i1, i2) -> i1 + ":" + i2);
		unit.accept(1, 2L);
		unit.accept(3, 4L);
		final Table<String, Long, Integer> actual = unit.build();
		Assertions.assertEquals(ImmutableTable.<String, Long, Integer>builder()
				.put("[1:2]", 3000L, 3)
				.put("[3:4]", 3000L, 3)
				.build(), actual);
		Assertions.assertInstanceOf(ImmutableTable.class, actual);
	}

}
