package lsh.ext.gson.merge;

import java.util.Collections;
import java.util.Map;

import com.google.gson.Gson;
import lsh.ext.gson.ParameterizedTypes;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public final class MergerTest {

	private static final Gson gson = new Gson();

	@Test
	public void testMergeMap() {
		final Map<Integer, String> map = Factories.linkedHashMap(1, "one", 2, "two", 3, "threa");
		final IMerger unit = Merger.getMerger(gson);
		final Map<Integer, String> mergedMap = unit.merge(map, gson -> gson.fromJson("{\"3\":\"three\",\"4\":\"four\"}", ParameterizedTypes.mapOf(Integer.class, String.class)));
		MatcherAssert.assertThat(mergedMap, CoreMatchers.sameInstance(map));
		MatcherAssert.assertThat(mergedMap, CoreMatchers.is(Factories.linkedHashMap(1, "one", 2, "two", 3, "three", 4, "four")));
	}

	@Test
	public void testMergeMapWithNestedMap() {
		final Map<Integer, String> map = Factories.linkedHashMap(1, "one", 2, "too", 3, "threa");
		final IMerger unit = Merger.getMerger(gson);
		final Map<Integer, String> mergedMap = unit.merge(map, gson -> gson.fromJson("{\"1\":null,\"2\":two,\"3\":\"three\",\"4\":\"four\",\"5\":{}}", ParameterizedTypes.mapOf(Integer.class, Object.class)));
		MatcherAssert.assertThat(mergedMap, CoreMatchers.sameInstance(map));
		MatcherAssert.assertThat(mergedMap, CoreMatchers.is(Factories.linkedHashMap(1, null, 2, "two", 3, "three", 4, "four", 5, Collections.emptyMap())));
	}

	@Test
	public void testMergeObject() {
		final Foo foo = new Foo("FOO", null);
		final IMerger unit = Merger.getMerger(gson);
		final Foo mergedFoo = unit.merge(foo, gson -> gson.fromJson("{\"bar\":\"BAR\"}", Foo.class));
		MatcherAssert.assertThat(mergedFoo, CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(mergedFoo.foo, CoreMatchers.is("FOO"));
		MatcherAssert.assertThat(mergedFoo.bar, CoreMatchers.is("BAR"));
		MatcherAssert.assertThat(mergedFoo.ref, CoreMatchers.nullValue());
	}

	@Test
	public void testMergeObjectWithSelfReference() {
		final Foo foo = new Foo("FOO", null);
		final IMerger unit = Merger.getMerger(gson);
		final Foo mergedFoo = unit.merge(foo, gson -> gson.fromJson("{\"bar\":\"BAR\",\"ref\":{\"bar\":\"foobar\"}}", Foo.class));
		MatcherAssert.assertThat(mergedFoo, CoreMatchers.sameInstance(foo));
		MatcherAssert.assertThat(mergedFoo.foo, CoreMatchers.is("FOO"));
		MatcherAssert.assertThat(mergedFoo.bar, CoreMatchers.is("BAR"));
		MatcherAssert.assertThat(mergedFoo.ref.foo, CoreMatchers.nullValue());
		MatcherAssert.assertThat(mergedFoo.ref.bar, CoreMatchers.is("foobar"));
		MatcherAssert.assertThat(mergedFoo.ref.ref, CoreMatchers.nullValue());
	}

	private static final class Foo {

		private final String foo;
		private final String bar;
		private Foo ref;

		private Foo(final String foo, final String bar) {
			this.foo = foo;
			this.bar = bar;
		}

	}

}
