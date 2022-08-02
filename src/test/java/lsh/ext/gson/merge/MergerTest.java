package lsh.ext.gson.merge;

import java.util.Collections;
import java.util.Map;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lsh.ext.gson.GsonBuilders;
import lsh.ext.gson.ParameterizedTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class MergerTest {

	private static final Gson gson = GsonBuilders.createCanonical()
			.create();

	@Test
	public void testMergeMap() {
		final Map<Integer, String> map = Factories.linkedHashMap(1, "one", 2, "two", 3, "threa");
		final IMerger unit = Merger.getInstance(gson);
		final Map<Integer, String> mergedMap = unit.merge(map, gson -> gson.fromJson("{\"3\":\"three\",\"4\":\"four\"}", ParameterizedTypes.mapOf(Integer.class, String.class)));
		Assertions.assertSame(map, mergedMap);
		Assertions.assertEquals(Factories.linkedHashMap(1, "one", 2, "two", 3, "three", 4, "four"), mergedMap);
	}

	@Test
	public void testMergeMapWithNestedMap() {
		final Map<Integer, String> map = Factories.linkedHashMap(1, "one", 2, "too", 3, "threa");
		final IMerger unit = Merger.getInstance(gson);
		final Map<Integer, String> mergedMap = unit.merge(map, gson -> gson.fromJson("{\"1\":null,\"2\":two,\"3\":\"three\",\"4\":\"four\",\"5\":{}}", ParameterizedTypes.mapOf(Integer.class, Object.class)));
		Assertions.assertSame(map, mergedMap);
		Assertions.assertEquals(Factories.linkedHashMap(1, null, 2, "two", 3, "three", 4, "four", 5, Collections.emptyMap()), mergedMap);
	}

	@Test
	public void testMergeObject() {
		final Foo foo = new Foo("FOO", null);
		final IMerger unit = Merger.getInstance(gson);
		final Foo mergedFoo = unit.merge(foo, gson -> gson.fromJson("{\"bar\":\"BAR\"}", Foo.class));
		Assertions.assertSame(foo, mergedFoo);
		Assertions.assertEquals("FOO", mergedFoo.foo);
		Assertions.assertEquals("BAR", mergedFoo.bar);
		Assertions.assertNull(mergedFoo.ref);
	}

	@Test
	public void testMergeObjectWithSelfReference() {
		final Foo foo = new Foo("FOO", null);
		final IMerger unit = Merger.getInstance(gson);
		final Foo mergedFoo = unit.merge(foo, gson -> gson.fromJson("{\"bar\":\"BAR\",\"ref\":{\"bar\":\"foobar\"}}", Foo.class));
		Assertions.assertSame(foo, mergedFoo);
		Assertions.assertEquals("FOO", mergedFoo.foo);
		Assertions.assertEquals("BAR", mergedFoo.bar);
		Assertions.assertNull(mergedFoo.ref.foo);
		Assertions.assertEquals("foobar", mergedFoo.ref.bar);
		Assertions.assertNull(mergedFoo.ref.ref);
	}

	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	private static final class Foo {

		private final String foo;
		private final String bar;
		private Foo ref;

	}

}
