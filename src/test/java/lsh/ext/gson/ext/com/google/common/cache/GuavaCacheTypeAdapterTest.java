package lsh.ext.gson.ext.com.google.common.cache;

import java.io.IOException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.TypeAdapter;
import lsh.ext.gson.test.TestTypeAdapters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class GuavaCacheTypeAdapterTest {

	private static final CacheLoader<String, Integer> cacheLoader = CacheLoader.from(String::length);

	@Test
	public void testWriteReadRoundTrip()
			throws IOException {
		final TypeAdapter<LoadingCache<String, Integer>> unit = GuavaCacheTypeAdapter.forCache(
				TestTypeAdapters.integerTypeAdapter,
				() -> CacheBuilder.newBuilder()
						.build(cacheLoader)
		);
		final LoadingCache<String, Integer> before = CacheBuilder.newBuilder()
				.build(cacheLoader);
		before.getUnchecked("foo");
		before.getUnchecked("bar");
		final String json = unit.toJson(before);
		Assertions.assertEquals("{\"foo\":3,\"bar\":3}", json);
		final LoadingCache<String, Integer> after = unit.fromJson(json);
		Assertions.assertEquals(before.asMap(), after.asMap());
	}

}
