package lsh.ext.gson.merge;

import java.util.Map;

import com.google.gson.Gson;
import org.junit.Test;

import static java.util.Collections.emptyMap;

import static lsh.ext.gson.ParameterizedTypes.mapOf;
import static lsh.ext.gson.merge.Factories.linkedHashMap;
import static lsh.ext.gson.merge.Merger.getMerger;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

public final class MergerTest {

	private static final Gson gson = new Gson();

	@Test
	public void testMergeMap() {
		final Map<Integer, String> map = linkedHashMap(1, "one", 2, "two", 3, "threa");
		final IMerger unit = getMerger(gson);
		final Map<Integer, String> mergedMap = unit.merge(map, gson -> gson.fromJson("{\"3\":\"three\",\"4\":\"four\"}", mapOf(Integer.class, String.class)));
		assertThat(mergedMap, sameInstance(map));
		assertThat(mergedMap, is(linkedHashMap(1, "one", 2, "two", 3, "three", 4, "four")));
	}

	@Test
	public void testMergeMapWithNestedMap() {
		final Map<Integer, String> map = linkedHashMap(1, "one", 2, "too", 3, "threa");
		final IMerger unit = getMerger(gson);
		final Map<Integer, String> mergedMap = unit.merge(map, gson -> gson.fromJson("{\"1\":null,\"2\":two,\"3\":\"three\",\"4\":\"four\",\"5\":{}}", mapOf(Integer.class, Object.class)));
		assertThat(mergedMap, sameInstance(map));
		assertThat(mergedMap, is(linkedHashMap(1, null, 2, "two", 3, "three", 4, "four", 5, emptyMap())));
	}

}
